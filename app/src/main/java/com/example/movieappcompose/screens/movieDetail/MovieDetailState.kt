package com.example.movieappcompose.screens.movieDetail

sealed class MovieDetailState {
    object Init:MovieDetailState()
    data class LoadedMovie(val movie:String):MovieDetailState()
    data class LoadedMovieDetails(val movie:String):MovieDetailState()
}