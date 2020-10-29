package com.example.movieappcompose.screens.mainActivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class MainActivityViewModel @ViewModelInject constructor(): ViewModel() {
    var showBottomNavigationBar: Boolean by mutableStateOf(true)
    var currentSelectedItemInNavigationBar: Int by mutableStateOf(0)

    fun changeBottomNavigationBarVisibility(){
        showBottomNavigationBar = !showBottomNavigationBar
    }
}