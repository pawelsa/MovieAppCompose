package com.example.movieappcompose.screens.moviePage.viewModel

import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.screens.moviePage.MoviePageState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() :
    BaseViewModel<MoviePageState>(MoviePageState.Init) {

    fun selectPage(page: Int) {
        state = MoviePageState.PageSelected(page)
    }
}