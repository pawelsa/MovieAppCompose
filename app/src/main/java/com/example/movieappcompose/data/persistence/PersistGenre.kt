package com.example.movieappcompose.data.persistence

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.movie.GenreListApi
import com.example.movieappcompose.data.dataSources.db.dao.GenreDao
import com.example.movieappcompose.data.datastore.Settings
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.data.models.movie.mappers.mapToDb
import com.example.movieappcompose.data.models.movie.mappers.mapToDomain
import com.example.movieappcompose.extensions.isDateOlderThan
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.util.*

abstract class PersistGenre : Persistence<List<Genre>, GenreListApi>()

class PersistGenreImpl(
    private val genreDao: GenreDao,
    private val moviesApi: MoviesApi,
    private val settings: Settings
) :
    PersistGenre() {

    /**
     * It is done this way so that we do not pass genres twice to [PersistGenre] listeners,
     * which may cause running another unnecessary rest queries
     */
    override fun get(): Observable<List<Genre>> {
        // TODO: 13/11/2020 check if comparing data works correctly
        return shouldUpdate()
                .flatMapObservable { shouldUpdate ->
                    if (shouldUpdate) {
                        getRemote()
                                .saveInDb()
                    } else {
                        getLocal()
                    }
                }
                .take(1)
    }

    override fun getLocal(): Observable<List<Genre>> {
        return genreDao
                .getAll()
                .map {
                    it.map { genreDb ->
                        Genre(
                            genre_id = genreDb.genre_id,
                            name = genreDb.name,
                        )
                    }
                }
                .toObservable()
    }

    override fun getRemote(): Observable<GenreListApi> {
        Timber.d("Started obtaining remote genres")
        return shouldUpdate()
                .filter { it }
                .flatMapObservable {
                    moviesApi
                            .getGenres()
                            .toObservable()
                }

    }

    private fun shouldUpdate(): Single<Boolean> {
        return settings
                .getLastTimeGenreMoviesSaved()
                .map {
                    it.isDateOlderThan(Calendar.DAY_OF_YEAR, 30)
                }
    }

    override fun Observable<GenreListApi>.saveInDb(): Observable<List<Genre>> {
        return this.flatMap {
            Observable
                    .fromCallable {
                        genreDao.insertAll(it.mapToDb())
                        it
                    }
                    .flatMapSingle { list ->
                        settings
                                .setLastTimeGenreSaved(System.currentTimeMillis())
                                .map { list }
                    }
                    .map { it.mapToDomain() }
        }
    }
}