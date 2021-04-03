package com.example.movieappcompose.screens.showDetail

import com.example.movieappcompose.data.models.tv_shows.DetailedShow

sealed class TvShowDetailState {
    object Init : TvShowDetailState()
    data class LoadedTvShow(val show: DetailedShow) : TvShowDetailState()
    data class LoadedTvShowDetails(val show: DetailedShow) : TvShowDetailState()
}