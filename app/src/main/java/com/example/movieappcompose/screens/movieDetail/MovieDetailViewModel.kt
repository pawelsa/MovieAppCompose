package com.example.movieappcompose.screens.movieDetail

import androidx.hilt.lifecycle.ViewModelInject
import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.usecase.GetReviewsUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MovieDetailViewModel @ViewModelInject constructor(
    private val getReviewsUseCase: GetReviewsUseCase
) : BaseViewModel<MovieDetailState>(
    MovieDetailState.Init
) {

    fun setMovie(movie: Movie) {
        state = MovieDetailState.LoadedMovie(movie)
        // TODO: 03/11/2020 get movie details and set new state
        disposables += getReviewsUseCase(GetReviewsUseCase.Params(movie.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ reviews ->
                    state = MovieDetailState.LoadedMovieDetails(
                        DetailedMovie(
                            movie = movie,
                            reviews = reviews,
                            discussion = emptyList()
                        )
                    )
                }, {
                    Timber.e(it)
                })
    }
}