package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.Review
import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.core.Single

interface GetReviewsUseCase : UseCase<GetReviewsUseCase.Params, Single<List<Review>>> {
    data class Params(val movieId: Int)
}

class GetReviewsUseCaseImpl(private val _movieRepository: MovieRepository) : GetReviewsUseCase {
    override fun run(param: GetReviewsUseCase.Params): Single<List<Review>> =
        _movieRepository.getMovieReviews(param.movieId)
}