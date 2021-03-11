package com.example.movieappcompose.di

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.db.dao.GenreDao
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.datastore.Settings
import com.example.movieappcompose.data.persistence.PersistGenre
import com.example.movieappcompose.data.persistence.PersistGenreImpl
import com.example.movieappcompose.data.persistence.PersistPopularMoviesList
import com.example.movieappcompose.data.persistence.PersistUpcomingMoviesList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun providesPersistGenre(
        genreDao: GenreDao,
        moviesApi: MoviesApi,
        settings: Settings
    ): PersistGenre = PersistGenreImpl(genreDao, moviesApi, settings)

    @Provides
    @Singleton
    fun providesPersistPopularMoviesList(
        movieDao: MovieDao,
        moviesApi: MoviesApi,
        persistGenre: PersistGenre,
        settings: Settings
    ): PersistPopularMoviesList =
        PersistPopularMoviesList(movieDao, moviesApi, persistGenre, settings)

    @Provides
    @Singleton
    fun providesPersistUpcomingMoviesList(
        movieDao: MovieDao,
        moviesApi: MoviesApi,
        persistGenre: PersistGenre,
        settings: Settings
    ): PersistUpcomingMoviesList =
        PersistUpcomingMoviesList(movieDao, moviesApi, persistGenre, settings)

}