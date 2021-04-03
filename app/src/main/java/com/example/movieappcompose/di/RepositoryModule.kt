package com.example.movieappcompose.di

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.TvShowsApi
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.dataSources.db.dao.TvShowDao
import com.example.movieappcompose.data.persistence.PersistPopularMoviesList
import com.example.movieappcompose.data.persistence.PersistPopularShowsList
import com.example.movieappcompose.data.persistence.PersistTopRatedShowsList
import com.example.movieappcompose.data.persistence.PersistUpcomingMoviesList
import com.example.movieappcompose.data.repositories.MovieRepository
import com.example.movieappcompose.data.repositories.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesMovieRepository(
        moviesApi: MoviesApi,
        persistPopularMoviesList: PersistPopularMoviesList,
        persistUpcomingMoviesList: PersistUpcomingMoviesList,
        movieDao: MovieDao
    ): MovieRepository =
        MovieRepository(moviesApi, persistPopularMoviesList, persistUpcomingMoviesList, movieDao)

    @Provides
    @Singleton
    fun providesTvShowRepository(
        tvShowsApi: TvShowsApi,
        persistPopularShowsList: PersistPopularShowsList,
        persistTopRatedShowsList: PersistTopRatedShowsList,
        tvShowDao: TvShowDao
    ): TvShowRepository =
        TvShowRepository(tvShowsApi, persistPopularShowsList, persistTopRatedShowsList, tvShowDao)
}