package com.example.movieappcompose.screens.moviePage.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.screens.moviePage.MoviePageState

class MainScreenViewModel @ViewModelInject constructor() :
    BaseViewModel<MoviePageState>(MoviePageState.Init) {

    fun selectPage(page:Int){
        state = MoviePageState.PageSelected(page)
    }
}