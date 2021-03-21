package com.example.movieappcompose.screens.tv_shows.viewModel

import com.example.movieappcompose.data.models.Movie

sealed class TvShowListState {
    object Init : TvShowListState()
    data class Loaded(val popularMovies: List<Movie>, val upcomingMovies: List<Movie>) :
        TvShowListState()
}
