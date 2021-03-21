package com.example.movieappcompose.di

import com.example.movieappcompose.data.repositories.MovieRepository
import com.example.movieappcompose.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesPopularMovies(movieRepository: MovieRepository): GetPopularMoviesUseCase =
        GetPopularMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun providesUpcomingMovies(movieRepository: MovieRepository): GetUpcomingMoviesUseCase =
        GetUpcomingMoviesUseCase(movieRepository)

    @Provides
    @Singleton
    fun providesMovieReviews(movieRepository: MovieRepository): GetDetailedMoviesUseCase =
        GetDetailedMoviesUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun providesMovieCollectedStatus(movieRepository: MovieRepository): ChangeMovieCollectedStatusUseCase =
        ChangeMovieCollectedStatusUseCaseImpl(movieRepository)
}