package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.base.BaseViewModel

class MovieDetailViewModel : BaseViewModel<MovieDetailState>(
    MovieDetailState.Init
){
    fun setMovie(movie: Long) {
        state = MovieDetailState.LoadedMovie(movie)
        // TODO: 03/11/2020 get movie details and set new state
    }
}