package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.core.Single

interface GetMoviesUseCase : UseCase<GetMoviesUseCase.Param, Single<List<Movie>>> {
    data class Param(val page: Int)
}

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) : GetMoviesUseCase {
    override fun run(param: GetMoviesUseCase.Param): Single<List<Movie>> =
        movieRepository.getPopularMovies(param.page)
}

class GetUpcomingMoviesUseCase(private val movieRepository: MovieRepository) : GetMoviesUseCase {
    override fun run(param: GetMoviesUseCase.Param): Single<List<Movie>> =
        movieRepository.getUpcomingMovies(param.page)
}