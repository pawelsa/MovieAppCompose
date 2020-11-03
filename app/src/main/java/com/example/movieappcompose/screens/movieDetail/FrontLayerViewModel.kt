package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.base.BaseViewModel

class FrontLayerViewModel : BaseViewModel<FrontLayerState>(
    FrontLayerState.Init
){
    fun selectPage(page:Int){
        state = FrontLayerState.SelectedPage(page)
    }
}