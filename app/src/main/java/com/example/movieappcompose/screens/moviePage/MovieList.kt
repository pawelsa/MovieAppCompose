package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import com.example.movieappcompose.data.models.Movie


@Composable
fun MainScreenMovieList(movieList: List<Movie>, onItemClick: (Movie) -> Unit) {
    // TODO: 05/11/2020 add pagination
    LazyColumnForIndexed(
        items = movieList
    ) { _, movie ->
        MovieCard(
            movie = movie,
            onClick = {
                // TODO: 04/11/2020 movie id or model should be passed
                onItemClick(movie)
            })
    }
}
