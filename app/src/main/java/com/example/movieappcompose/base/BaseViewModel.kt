package com.example.movieappcompose.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T>(initState:T) : ViewModel(){
    var state:T by mutableStateOf(initState)
}