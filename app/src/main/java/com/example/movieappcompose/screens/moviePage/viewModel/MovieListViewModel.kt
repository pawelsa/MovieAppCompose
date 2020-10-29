package com.example.movieappcompose.screens.moviePage.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.movieappcompose.usecase.GetMoviesUseCase
import com.example.movieappcompose.usecase.GetPopularMoviesUseCase
import com.example.movieappcompose.usecase.GetUpcomingMoviesUseCase

abstract class BaseMoviesListViewModel(private val getMoviesUseCase: GetMoviesUseCase) : ViewModel() {
    var movieList: List<String> by mutableStateOf(listOf())
        protected set
    fun getMovies(){
        movieList = getMoviesUseCase(Unit)
    }
}

class PopularMoviesViewModel @ViewModelInject constructor(getMoviesUseCase: GetPopularMoviesUseCase): BaseMoviesListViewModel(getMoviesUseCase)

class UpcomingMoviesViewModel @ViewModelInject constructor(getMoviesUseCase: GetUpcomingMoviesUseCase):
    BaseMoviesListViewModel(getMoviesUseCase)