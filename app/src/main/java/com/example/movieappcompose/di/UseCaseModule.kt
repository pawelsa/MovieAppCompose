package com.example.movieappcompose.di

import com.example.movieappcompose.data.repositories.MovieRepository
import com.example.movieappcompose.data.repositories.TvShowRepository
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
    fun providesDetailedMovie(movieRepository: MovieRepository): GetDetailedMoviesUseCase =
        GetDetailedMoviesUseCaseImpl(movieRepository)

    @Provides
    @Singleton
    fun providesMovieCollectedStatus(movieRepository: MovieRepository): ChangeMovieCollectedStatusUseCase =
        ChangeMovieCollectedStatusUseCaseImpl(movieRepository)

    @Provides
    @Singleton // TODO: 01/04/2021 move persistence to repository
    fun providersPopularShows(tvShowRepository: TvShowRepository): GetPopularShowsUseCase =
        GetPopularShowsUseCase(tvShowRepository)

    @Provides
    @Singleton
    fun providersTopRatedShows(tvShowRepository: TvShowRepository): GetTopRatedShowsUseCase =
        GetTopRatedShowsUseCase(tvShowRepository)

    @Provides
    @Singleton
    fun providesDetailedTvShow(tvShowRepository: TvShowRepository): GetDetailedTvShowUseCase =
        GetDetailedTvShowUseCaseImpl(tvShowRepository)

    @Provides
    @Singleton
    fun providersCollectedTvShowStatus(tvShowRepository: TvShowRepository): ChangeTvShowCollectedStatusUseCase =
        ChangeTvShowCollectedStatusUseCaseImpl(tvShowRepository)
}