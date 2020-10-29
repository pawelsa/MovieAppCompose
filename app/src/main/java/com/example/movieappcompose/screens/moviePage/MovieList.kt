package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.movieappcompose.base.OnItemClick
import com.example.movieappcompose.screens.moviePage.viewModel.BaseMoviesListViewModel
import com.example.movieappcompose.widgets.Center


@Composable
fun MainScreenMovieList(viewModel: BaseMoviesListViewModel) {
    // TODO: 28/10/2020 save scroll position
    val movieList: List<String> = viewModel.movieList
    if (movieList.isEmpty()) {
        viewModel.getMovies()
    }
    MainScreenMovieList(
        movieList = viewModel.movieList
    ){}
}

@Composable
fun MainScreenMovieList(movieList: List<String>, onItemClick: OnItemClick) {
    if (movieList.isEmpty()) {
        Center {
            CircularProgressIndicator()
        }
    } else {
        LazyColumnForIndexed(
            items = movieList
        ) { index, item ->
            MovieCard(
                title = item,
                onClick = {
                    onItemClick(index)
                })
        }
    }
}