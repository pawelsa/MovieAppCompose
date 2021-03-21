package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.usecase.ChangeMovieCollectedStatusUseCase
import com.example.movieappcompose.usecase.GetDetailedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getDetailedMoviesUseCase: GetDetailedMoviesUseCase,
    private val changeMovieCollectedStatusUseCase: ChangeMovieCollectedStatusUseCase
) : BaseViewModel<MovieDetailState>(
    MovieDetailState.Init
) {
    private lateinit var movie: DetailedMovie

    fun setMovie(movie: Movie) {
        this.movie = DetailedMovie(movie = movie, reviews = emptyList(), discussion = emptyList())
        state = MovieDetailState.LoadedMovie(this.movie)
        // TODO: 03/11/2020 get movie details and set new state
        disposables += getDetailedMoviesUseCase(GetDetailedMoviesUseCase.Params(movie))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ detailedMovie ->
                this.movie = detailedMovie
                state = MovieDetailState.LoadedMovieDetails(this.movie)
            }, {
                Timber.e(it)
            })
    }

    fun changeCollectedStatus(movie: Movie) {
        disposables += changeMovieCollectedStatusUseCase(
            ChangeMovieCollectedStatusUseCase.Param(
                movie.id,
                this.movie.isCollected
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.movie = this.movie.copy(isCollected = it)
                state = MovieDetailState.LoadedMovieDetails(this.movie)
            }, {
                Timber.e(it)
            })
    }
}