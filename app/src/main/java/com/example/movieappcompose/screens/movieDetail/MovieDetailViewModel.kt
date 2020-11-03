package com.example.movieappcompose.screens.movieDetail

import com.example.movieappcompose.base.BaseViewModel

class MovieDetailViewModel : BaseViewModel<MovieDetailState>(
    MovieDetailState.Init
){
    fun setMovie(movie:String){
        state = MovieDetailState.LoadedMovie(movie)
    }
}