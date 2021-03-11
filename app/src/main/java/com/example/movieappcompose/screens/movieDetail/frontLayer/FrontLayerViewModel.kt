package com.example.movieappcompose.screens.movieDetail.frontLayer

import com.example.movieappcompose.base.BaseViewModel

class FrontLayerViewModel : BaseViewModel<FrontLayerState>(
    FrontLayerState.Init
){
    fun selectPage(page:Int){
        state = FrontLayerState.SelectedPage(page)
    }
}