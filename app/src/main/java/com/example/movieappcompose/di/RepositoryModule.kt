package com.example.movieappcompose.di

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.db.MovieDao
import com.example.movieappcompose.data.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesMovieRepository(moviesApi: MoviesApi, moviesDao: MovieDao): MovieRepository =
        MovieRepository(moviesApi, moviesDao)
}