package com.example.movieappcompose.screens.moviePage

sealed class MoviePageState(val pageSelected: Int) {
    object Init : MoviePageState(0)
    data class PageSelected(val page: Int): MoviePageState(page)
}