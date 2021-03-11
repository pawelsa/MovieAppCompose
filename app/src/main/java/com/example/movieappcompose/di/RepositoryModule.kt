package com.example.movieappcompose.di

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.persistence.PersistPopularMoviesList
import com.example.movieappcompose.data.persistence.PersistUpcomingMoviesList
import com.example.movieappcompose.data.repositories.MovieRepository
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
        persistUpcomingMoviesList: PersistUpcomingMoviesList
    ): MovieRepository =
        MovieRepository(moviesApi, persistPopularMoviesList, persistUpcomingMoviesList)
}