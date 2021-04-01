package com.example.movieappcompose.data.datastore

import androidx.datastore.DataStore
import com.example.movieappcompose.LastUpdated
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx3.asObservable
import kotlinx.coroutines.rx3.rxSingle

class Settings(private val dataStore: DataStore<LastUpdated>) {
    fun setLastTimePopularMoviesSaved(timestamp: Long): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setPopularMovies(timestamp)
                        .build()
            }.popularMovies
        }
    }

    fun setLastTimePopularShowsSaved(timestamp: Long): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setPopularShows(timestamp)
                        .build()
            }.popularShows
        }
    }

    @ExperimentalCoroutinesApi
    fun getLastTimePopularMoviesSaved(): Single<Long> {
        return dataStore.data
                .map { it.popularMovies }
                .asObservable()
                .firstOrError()
    }

    @ExperimentalCoroutinesApi
    fun getLastTimePopularShowsSaved(): Single<Long> {
        return dataStore.data
                .map { it.popularShows }
                .asObservable()
                .firstOrError()
    }

    @ExperimentalCoroutinesApi
    fun getLastTimeTopRatedShowsSaved(): Single<Long> {
        return dataStore.data
                .map { it.topRatedShows }
                .asObservable()
                .firstOrError()
    }

    fun setLastTimeUpcomingMoviesSaved(timestamp: Long): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setPopularMovies(timestamp)
                        .build()
            }.popularMovies
        }
    }

    fun setLastTimeTopRatedShowsSaved(timestamp: Long): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setTopRatedShows(timestamp)
                        .build()
            }.topRatedShows
        }
    }

    @ExperimentalCoroutinesApi
    fun getLastTimeUpcomingMoviesSaved(): Single<Long> {
        return dataStore.data
                .map { it.popularMovies }
                .asObservable()
                .firstOrError()
    }

    fun setLastTimeGenreSaved(timestamp: Long): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setGenres(timestamp)
                        .build()
            }.popularMovies
        }
    }

    @ExperimentalCoroutinesApi
    fun getLastTimeGenreMoviesSaved(): Single<Long> {
        return dataStore.data
                .map { it.genres }
                .asObservable()
                .firstOrError()
    }

    fun setPopularMoviesLastPage(lastPage: Int): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setPopularMoviesPages(lastPage)
                        .build()
            }.popularMovies
        }
    }

    fun setPopularShowsLastPage(lastPage: Int): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setPopularShowsPages(lastPage)
                        .build()
            }.popularShows
        }
    }

    @ExperimentalCoroutinesApi
    fun getPopularMoviesLastPage(): Single<Int> {
        return dataStore.data
                .map { it.popularMoviesPages }
                .asObservable()
                .firstOrError()
    }

    @ExperimentalCoroutinesApi
    fun getPopularShowsLastPage(): Single<Int> {
        return dataStore.data
                .map { it.popularShowsPages }
                .asObservable()
                .firstOrError()
    }

    @ExperimentalCoroutinesApi
    fun getTopRatedShowsLastPage(): Single<Int> {
        return dataStore.data
                .map { it.topRatedShowsPages }
                .asObservable()
                .firstOrError()
    }

    fun setTopRatedShowLastPage(lastPage: Int): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setTopRatedShowsPages(lastPage)
                        .build()
            }.topRatedShows
        }
    }

    fun setUpcomingMoviesLastPage(lastPage: Int): Single<Long> {
        return rxSingle {
            dataStore.updateData {
                it
                        .toBuilder()
                        .setUpcomingMoviesPages(lastPage)
                        .build()
            }.popularMovies
        }
    }

    @ExperimentalCoroutinesApi
    fun getUpcomingMoviesLastPage(): Single<Int> {
        return dataStore.data
                .map { it.upcomingMoviesPages }
                .asObservable()
                .firstOrError()
    }

}
