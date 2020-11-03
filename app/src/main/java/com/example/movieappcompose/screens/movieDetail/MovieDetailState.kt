package com.example.movieappcompose.screens.movieDetail

sealed class MovieDetailState {
    object Init : MovieDetailState()
    data class LoadedMovie(val movie: Long) : MovieDetailState()
    data class LoadedMovieDetails(val movie: Long) : MovieDetailState()
}