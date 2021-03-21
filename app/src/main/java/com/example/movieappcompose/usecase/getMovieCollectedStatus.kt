package com.example.movieappcompose.usecase

import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.core.Single

interface ChangeMovieCollectedStatusUseCase{
    operator fun invoke(param: Param) : Single<Boolean>

    data class Param(val movieId: Int)
}

class ChangeMovieCollectedStatusUseCaseImpl(private val movieRepository: MovieRepository,): ChangeMovieCollectedStatusUseCase{
    override fun invoke(param: ChangeMovieCollectedStatusUseCase.Param): Single<Boolean> = movieRepository.changeMovieCollectStatus(param.movieId)
}