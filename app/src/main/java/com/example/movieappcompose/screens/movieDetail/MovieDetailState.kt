package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.data.models.movie.DetailedMovie

sealed class MovieDetailState {
    object Init : MovieDetailState()
    data class LoadedMovie(val movie: DetailedMovie) : MovieDetailState()
    data class LoadedMovieDetails(val movie: DetailedMovie) : MovieDetailState()
}