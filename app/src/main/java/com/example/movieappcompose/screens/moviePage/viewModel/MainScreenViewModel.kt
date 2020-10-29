package com.example.movieappcompose.screens.moviePage.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class MainScreenViewModel @ViewModelInject constructor() : ViewModel(){
    var currentPage: Int by mutableStateOf(0)
    fun currentPage(page:Int){
        currentPage = page
    }
}