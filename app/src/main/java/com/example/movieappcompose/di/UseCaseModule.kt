package com.example.movieappcompose.di

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
    fun providesPopularMovies(): GetPopularMoviesUseCase = GetPopularMoviesUseCase()

    @Provides
    @Singleton
    fun providesUpcomingMovies(): GetUpcomingMoviesUseCase = GetUpcomingMoviesUseCase()

}