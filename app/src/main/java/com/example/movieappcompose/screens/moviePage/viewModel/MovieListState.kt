package com.example.movieappcompose.screens.moviePage.viewModel

import com.example.movieappcompose.data.models.Movie

sealed class MovieListState {
    object Init : MovieListState()
    data class Loaded(val popularMovies: List<Movie>, val upcomingMovies: List<Movie>) :
        MovieListState()
}
