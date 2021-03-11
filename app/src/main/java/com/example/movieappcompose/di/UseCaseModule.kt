package com.example.movieappcompose.di

import com.example.movieappcompose.data.repositories.MovieRepository
import com.example.movieappcompose.usecase.GetPopularMoviesUseCase
import com.example.movieappcompose.usecase.GetReviewsUseCase
import com.example.movieappcompose.usecase.GetReviewsUseCaseImpl
import com.example.movieappcompose.usecase.GetUpcomingMoviesUseCase
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
    fun providesMovieReviews(movieRepository: MovieRepository): GetReviewsUseCase =
        GetReviewsUseCaseImpl(movieRepository)
}