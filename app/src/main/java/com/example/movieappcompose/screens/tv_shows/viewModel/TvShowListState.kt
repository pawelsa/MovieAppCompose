package com.example.movieappcompose.screens.tv_shows.viewModel

import com.example.movieappcompose.data.models.tv_shows.TvShow

sealed class TvShowListState {
    object Init : TvShowListState()
    data class Loaded(val popularShows: List<TvShow>, val topShows: List<TvShow>) :
        TvShowListState()
}
