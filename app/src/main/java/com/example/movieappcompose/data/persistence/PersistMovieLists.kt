package com.example.movieappcompose.data.persistence

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.MovieApi
import com.example.movieappcompose.data.dataSources.api.models.MovieListApi
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.dataSources.db.models.MOVIE_POPULAR
import com.example.movieappcompose.data.dataSources.db.models.MOVIE_UPCOMING
import com.example.movieappcompose.data.dataSources.db.models.MovieOrderDb
import com.example.movieappcompose.data.datastore.Settings
import com.example.movieappcompose.data.models.Genre
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.models.mappers.ApiResponseToMovieDb
import com.example.movieappcompose.data.models.mappers.mapToDomain
import com.example.movieappcompose.extensions.isDateOlderThan
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.zipWith
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.rx3.asObservable
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

abstract class PersistMovieLists(
    protected val movieDao: MovieDao,
    protected val api: MoviesApi,
    private val persistGenre: PersistGenre,
    protected val settings: Settings,
    private val movieListType: Int
) : PersistenceWithParam<PersistMovieLists.Param, List<Movie>, List<PersistMovieLists.MovieTransfer>>() {

    data class Param(val page: Int)

    abstract fun remoteSource(page: Int): Single<MovieListApi>

    abstract fun shouldUpdateMovieData(page: Int): Single<Boolean>

    override fun getRemote(param: Param): Observable<List<MovieTransfer>> {
        return shouldUpdateMovieData(param.page)
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
                                                                    MovieTransfer(
                                                                        movieApi = movieWithNumber.first,
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

    private fun getCredits(movieId: Int): Single<CreditsApi> = api.getCredits(movieId = movieId)

    override fun Observable<List<MovieTransfer>>.saveInDb(param: Param): Observable<List<Movie>> {
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
        it: List<MovieTransfer>,
        page: Int
    ): @NonNull Single<List<MovieTransfer>> {
        return Observable
                .fromCallable {
                    if (page == 1) {
                        movieDao.deleteOrder(movieListType)
                    }
                    it
                }
                .flatMapIterable { it }
                .map {
                    val movieOrderDb = MovieOrderDb(
                        order = it.numberInOrder,
                        movie_id = it.movieApi.id,
                        type = movieListType
                    )
                    movieDao.insertOrder(movieOrderDb)
                    movieDao.insert(
                        ApiResponseToMovieDb(
                            it.movieApi,
                            it.genres,
                            it.creditsApi,
                            listOf(movieOrderDb)
                        )
                    )
                    it
                }
                .toList()
                .saveInSettings {
                    if (movieListType == MOVIE_POPULAR) {
                        setLastTimePopularMoviesSaved(System.currentTimeMillis()).flatMap {
                            setPopularMoviesLastPage(page)
                        }
                    } else {
                        setLastTimeUpcomingMoviesSaved(System.currentTimeMillis()).flatMap {
                            setUpcomingMoviesLastPage(page)
                        }
                    }
                }
    }

    private fun <T, R> Single<T>.saveInSettings(
        method: Settings.() -> Single<R>
    ): Single<T> =
        flatMap { data ->
            method(settings).map {
                data
            }
        }

    data class MovieTransfer(
        val movieApi: MovieApi,
        val genres: List<Genre>,
        val creditsApi: CreditsApi,
        val numberInOrder: Int
    )
}

class PersistPopularMoviesList(
    movieDao: MovieDao,
    api: MoviesApi,
    persistGenre: PersistGenre,
    settings: Settings
) :
    PersistMovieLists(movieDao, api, persistGenre, settings, MOVIE_POPULAR) {
    override fun remoteSource(page: Int): Single<MovieListApi> =
        api
                .getPopularMovies(page)
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

    override fun shouldUpdateMovieData(page: Int): Single<Boolean> {
        // TODO: 13/11/2020 check if can be changed to nested class in settings
        return settings
                .getLastTimePopularMoviesSaved()
                .flatMap { lastTimeUpdated ->
                    settings
                            .getPopularMoviesLastPage()
                            .map { lastPage ->
                                Timber.d("Popular remote: lastPage: $lastPage, page: $page, updated: $lastTimeUpdated")
                                lastPage < page || lastTimeUpdated.isDateOlderThan(
                                    Calendar.DAY_OF_MONTH,
                                    1
                                )
                            }
                }
    }

    override fun getLocal(param: Param): Observable<List<Movie>> {
        return movieDao
                .getPopularMovies()
                .take(1)
                .asObservable()
                .map {
                    it.mapToDomain()
                }
    }

}

class PersistUpcomingMoviesList(
    movieDao: MovieDao,
    api: MoviesApi,
    persistGenre: PersistGenre,
    settings: Settings
) :
    PersistMovieLists(movieDao, api, persistGenre, settings, MOVIE_UPCOMING) {
    override fun remoteSource(page: Int): Single<MovieListApi> =
        api
                .getUpcomingMovies(page)
                .timeout(20, TimeUnit.SECONDS)

    override fun shouldUpdateMovieData(page: Int): Single<Boolean> {
        return settings
                .getLastTimeUpcomingMoviesSaved()
                .flatMap { lastTimeUpdated ->
                    settings
                            .getUpcomingMoviesLastPage()
                            .map { lastPage ->
                                Timber.d("Upcoming remote: savedPages: $lastPage, page: $page, updated: $lastTimeUpdated")
                                lastPage < page || lastTimeUpdated.isDateOlderThan(
                                    Calendar.DAY_OF_MONTH,
                                    1
                                )
                            }
                }
    }

    override fun getLocal(param: Param): Observable<List<Movie>> {
        return movieDao
                .getUpcomingMovies()
                .take(1)
                .asObservable()
                .map {
                    it.mapToDomain()
                }
    }

}


