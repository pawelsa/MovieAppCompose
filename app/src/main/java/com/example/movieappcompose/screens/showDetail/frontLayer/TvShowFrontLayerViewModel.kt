package com.example.movieappcompose.screens.showDetail.frontLayer

import com.example.movieappcompose.base.BaseViewModel

class TvShowFrontLayerViewModel : BaseViewModel<TvShowFrontLayerState>(
    TvShowFrontLayerState.Init
) {
    fun selectPage(page: Int) {
        state = TvShowFrontLayerState.SelectedPage(page)
    }
}