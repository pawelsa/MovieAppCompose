package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.core.Observable

interface GetMoviesUseCase : UseCase<GetMoviesUseCase.Param, Observable<List<Movie>>> {
    data class Param(val page: Int)
}

class GetPopularMoviesUseCase(private val movieRepository: MovieRepository) : GetMoviesUseCase {
    override fun run(param: GetMoviesUseCase.Param): Observable<List<Movie>> =
        movieRepository.getPopularMovies(param.page)
}

class GetUpcomingMoviesUseCase(private val movieRepository: MovieRepository) : GetMoviesUseCase {
    override fun run(param: GetMoviesUseCase.Param): Observable<List<Movie>> =
        movieRepository.getUpcomingMovies(param.page)
}