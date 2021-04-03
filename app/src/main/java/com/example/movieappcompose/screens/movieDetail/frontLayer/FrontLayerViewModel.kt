package com.example.movieappcompose.screens.movieDetail.frontLayer

import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.screens.showDetail.frontLayer.TvShowFrontLayerState

class FrontLayerViewModel : BaseViewModel<TvShowFrontLayerState>(
    TvShowFrontLayerState.Init
) {
    fun selectPage(page: Int) {
        state = TvShowFrontLayerState.SelectedPage(page)
    }
}