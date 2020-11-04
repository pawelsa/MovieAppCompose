package com.example.movieappcompose.di

import com.example.movieappcompose.data.repositories.MovieRepository
import com.example.movieappcompose.usecase.GetPopularMoviesUseCase
import com.example.movieappcompose.usecase.GetUpcomingMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesPopularMovies(movieRepository: MovieRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun providesUpcomingMovies(movieRepository: MovieRepository): GetUpcomingMoviesUseCase =
        GetUpcomingMoviesUseCase(movieRepository)

}