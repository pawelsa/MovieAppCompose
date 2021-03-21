package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie

sealed class MovieDetailState {
    object Init : MovieDetailState()
    data class LoadedMovie(val movie: DetailedMovie) : MovieDetailState()
    data class LoadedMovieDetails(val movie: DetailedMovie) : MovieDetailState()
}