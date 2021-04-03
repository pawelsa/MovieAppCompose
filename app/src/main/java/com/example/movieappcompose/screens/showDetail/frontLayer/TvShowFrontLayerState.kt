package com.example.movieappcompose.screens.showDetail.frontLayer

sealed class TvShowFrontLayerState(val page: Int) {
    object Init : TvShowFrontLayerState(0)
    data class SelectedPage(val selectedPage: Int) : TvShowFrontLayerState(selectedPage)
}
