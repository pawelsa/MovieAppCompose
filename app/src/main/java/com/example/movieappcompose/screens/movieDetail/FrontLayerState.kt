package com.example.movieappcompose.screens.movieDetail

sealed class FrontLayerState(val page: Int) {
    object Init:FrontLayerState(0)
    data class SelectedPage(val selectedPage:Int): FrontLayerState(selectedPage)
}
