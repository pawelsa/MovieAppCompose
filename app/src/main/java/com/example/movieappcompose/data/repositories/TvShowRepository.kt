package com.example.movieappcompose.data.repositories

import com.example.movieappcompose.data.dataSources.api.TvShowsApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.ShowCreditsApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.TvShowApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.TvShowsListApi
import com.example.movieappcompose.data.dataSources.db.dao.TvShowDao
import com.example.movieappcompose.data.dataSources.db.models.*
import com.example.movieappcompose.data.dataSources.db.models.movie.MOVIE_POPULAR
import com.example.movieappcompose.data.dataSources.db.models.movie.MOVIE_UPCOMING
import com.example.movieappcompose.data.dataSources.db.models.movie.TvShowOrderDb
import com.example.movieappcompose.data.datastore.Settings
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.models.tv_shows.mappers.ApiShowToDb
import com.example.movieappcompose.data.models.tv_shows.mappers.mapToDomain
import com.example.movieappcompose.data.persistence.PersistGenre
import com.example.movieappcompose.data.persistence.PersistenceWithParam
import com.example.movieappcompose.extensions.isDateOlderThan
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.zipWith
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

abstract class PersistTvShowLists(
    protected val tvShowDao: TvShowDao,
    protected val api: TvShowsApi,
    private val persistGenre: PersistGenre,
    protected val settings: Settings,
    private val tvShowListType: Int,
) : PersistenceWithParam<PersistTvShowLists.Param, List<TvShow>, List<PersistTvShowLists.TvShowTransfer>>() {

    data class Param(val page: Int)

    abstract fun remoteSource(page: Int): Single<TvShowsListApi>

    abstract fun shouldUpdateTvShowData(page: Int): Single<Boolean>

    override fun getRemote(param: Param): Observable<List<TvShowTransfer>> {
        return shouldUpdateTvShowData(param.page)
                .filter { it }
                .flatMapObservable {
                    Timber.d("Should download data from remote")
                    persistGenre
                            .get()
                            .concatMap { genreList: List<Genre> ->
                                remoteSource(param.page)
                                        .map { it.results }
                                        .flatMapObservable {
                                            Timber.d("Remote size list: ${it.size}")
                                            Flowable
                                                    .fromIterable(it)
                                                    .zipWith(Flowable.create<Int>({ emitter ->
                                                        var count = (param.page - 1) * 20
                                                        while (true) {
                                                            emitter.onNext(count++)
                                                        }
                                                    }, BackpressureStrategy.BUFFER))
                                                    .parallel()
                                                    .flatMap { movieWithNumber ->
                                                        getCredits(movieWithNumber.first.id)
                                                                .map { credits ->
                                                                    TvShowTransfer(
                                                                        tvShowApi = movieWithNumber.first,
                                                                        genres = genreList,
                                                                        creditsApi = credits,
                                                                        numberInOrder = movieWithNumber.second
                                                                    )
                                                                }
                                                                .toFlowable()
                                                    }
                                                    .sequential()
                                                    .toList()
                                                    .toObservable()
                                            // TODO: 04/11/2020 test how parallel might improve the results,
                                            //  because sorting list might be more demanding
                                            //  also it should keep the order of the movies
                                            //  it may be better if copied to another file, made as one big method
                                            //  and then inserted parallel, after that make it back into persistence

                                        }
                            }
                }
    }

    private fun getCredits(movieId: Int): Single<ShowCreditsApi> = api.getCredits(showId = movieId)

    override fun Observable<List<TvShowTransfer>>.saveInDb(param: Param): Observable<List<TvShow>> {
        return this
                .flatMapSingle {
                    saveMovieInDatabaseWithOrder(it, param.page)
                }
                .doOnNext {
                    Timber.d("After saving in db: ${it.size}")
                }
                .flatMap { getLocal(param) }
                .doOnNext {
                    Timber.d("After reading from db: ${it.size}")
                }
                .onErrorReturn {
                    // TODO: 13/11/2020 should inform UI about network connection error
                    Timber.e(it)
                    listOf()
                }
    }

    private fun saveMovieInDatabaseWithOrder(
        showTransferList: List<TvShowTransfer>,
        page: Int,
    ): @NonNull Single<List<TvShowTransfer>> {
        return Single
                .just(showTransferList)
                .flatMap {
                    if (page == 1) {
                        return@flatMap tvShowDao
                                .deleteOrder(tvShowListType)
                                .andThen(Single.just(showTransferList))
                    }
                    return@flatMap Single.just(showTransferList)
                }
                .map {
                    val moviePairs = it.map {
                        val tvShowOrderDb = TvShowOrderDb(
                            order = it.numberInOrder,
                            show_id = it.tvShowApi.id,
                            type = tvShowListType
                        )
                        // TODO: 30/03/2021 this should map to db not domain
                        val tvShowDb = ApiShowToDb(
                            it.tvShowApi,
                            it.genres,
                            it.creditsApi,
                            listOf(tvShowOrderDb)
                        )
                        tvShowOrderDb to tvShowDb
                    }
                    moviePairs
                }
                .flatMap {
                    tvShowDao
                            .insertAll(it.map { it.second })
                            .concatWith(
                                tvShowDao.insertAllOrders(it.map { it.first })
                            )
                            .andThen(Single.just(showTransferList))
                }
                .saveInSettings {
                    if (tvShowListType == MOVIE_POPULAR) {
                        setLastTimePopularShowsSaved(System.currentTimeMillis()).flatMap {
                            setPopularShowsLastPage(page)
                        }
                    } else {
                        setLastTimeTopRatedShowsSaved(System.currentTimeMillis()).flatMap {
                            setUpcomingMoviesLastPage(page)
                        }
                    }
                }
    }

    private fun <T, R> Single<T>.saveInSettings(
        method: Settings.() -> Single<R>,
    ): Single<T> =
        flatMap { data ->
            method(settings).map {
                data
            }
        }

    data class TvShowTransfer(
        val tvShowApi: TvShowApi,
        val genres: List<Genre>,
        val creditsApi: ShowCreditsApi,
        val numberInOrder: Int,
    )
}

class PersistPopularShowsList(
    tvShowDao: TvShowDao,
    api: TvShowsApi,
    persistGenre: PersistGenre,
    settings: Settings,
) :
    PersistTvShowLists(tvShowDao, api, persistGenre, settings, MOVIE_POPULAR) {
    override fun remoteSource(page: Int): Single<TvShowsListApi> =
        api
                .getPopularShows(page)
                .timeout(20, TimeUnit.SECONDS)
                .doOnSubscribe {
                    Timber.d("Subscribed to remote")
                }
                .doOnSuccess {
                    Timber.d("Succeeded remote download")
                }
                .doOnError {
                    Timber.e(it, "Thrown exception at remote download")
                }

    override fun shouldUpdateTvShowData(page: Int): Single<Boolean> {
        // TODO: 13/11/2020 check if can be changed to nested class in settings
        return settings
                .getLastTimePopularShowsSaved()
                .flatMap { lastTimeUpdated ->
                    settings
                            .getPopularShowsLastPage()
                            .map { lastPage ->
                                Timber.d("Popular remote: lastPage: $lastPage, page: $page, updated: $lastTimeUpdated")
                                lastPage < page || lastTimeUpdated.isDateOlderThan(
                                    Calendar.DAY_OF_MONTH,
                                    1
                                )
                            }
                }
    }

    override fun getLocal(param: Param): Observable<List<TvShow>> {
        return tvShowDao
                .getTopRatedShows()
                .map {
                    it.mapToDomain()
                }
                .toObservable()
    }

}

class PersistTopRatedShowsList(
    tvShowDao: TvShowDao,
    api: TvShowsApi,
    persistGenre: PersistGenre,
    settings: Settings,
) :
    PersistTvShowLists(tvShowDao, api, persistGenre, settings, MOVIE_UPCOMING) {
    override fun remoteSource(page: Int): Single<TvShowsListApi> =
        api
                .getTopRatedShows(page)
                .timeout(20, TimeUnit.SECONDS)

    override fun shouldUpdateTvShowData(page: Int): Single<Boolean> {
        return settings
                .getLastTimeTopRatedShowsSaved()
                .flatMap { lastTimeUpdated ->
                    settings
                            .getTopRatedShowsLastPage()
                            .map { lastPage ->
                                Timber.d("Upcoming remote: savedPages: $lastPage, page: $page, updated: $lastTimeUpdated")
                                lastPage < page || lastTimeUpdated.isDateOlderThan(
                                    Calendar.DAY_OF_MONTH,
                                    1
                                )
                            }
                }
    }

    override fun getLocal(param: Param): Observable<List<TvShow>> {
        return tvShowDao
                .getPopularShows()
                .map {
                    it.mapToDomain()
                }
                .toObservable()
    }

}


/*

import com.example.movieappcompose.data.dataSources.api.TvShowsApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.TvShowApi
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.models.tv_shows.mappers.ApiCastToDomain
import com.example.movieappcompose.data.models.tv_shows.mappers.ApiListToDomainShow
import com.example.movieappcompose.data.models.tv_shows.mappers.ApiToCrewDomain
import com.example.movieappcompose.data.models.tv_shows.mappers.ApiToDomainShow
import com.example.movieappcompose.data.persistence.PersistGenre
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable


class TvShowRepository(private val tvShowsApi: TvShowsApi, private val persistGenre: PersistGenre) {

    fun getTopRatedTvShows(page: Int): Observable<List<TvShow>> = persistGenre
            .get()
            .flatMap { genreList ->
                return@flatMap tvShowsApi
                        .getTopRatedShows(page)
                        .map { it.results }
                        .flatMapObservable {
                            _some(it, genreList)
                        }
            }

    private fun _some(
        it: List<TvShowApi>?,
        genreList: List<Genre>,
    ): Observable<TvShow> = Flowable
            .fromIterable(it).parallel()
            .flatMap { apiShow ->
                tvShowsApi
                        .getCredits(apiShow.id)
                        .map { credits ->
                            ApiToDomainShow(apiShow,
                                genreList,
                                credits.crew.map { ApiToCrewDomain(it) },
                                credits.cast.map { ApiCastToDomain(it)})
                        }
            }

    fun getPopularTvShows(page: Int): Observable<List<TvShow>> = persistGenre.get().flatMap { genreList ->
        return@flatMap tvShowsApi
                .getPopularShows(page)
                .map { ApiListToDomainShow(it, genreList) }
                .toObservable()
    }

}*/
