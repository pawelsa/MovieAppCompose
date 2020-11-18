package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import com.example.movieappcompose.data.models.Movie
import timber.log.Timber


@Composable
fun MainScreenMovieList(
    movieList: List<Movie>,
    loadMoreData: () -> Unit = {},
    onItemClick: (Movie) -> Unit
) {
    // TODO: 05/11/2020 add pagination

    LazyColumnForIndexed(
        items = movieList
    ) { index, movie ->
        MovieCard(
            movie = movie,
            onClick = {
                // TODO: 04/11/2020 movie id or model should be passed
                onItemClick(movie)
            })
        if (movieList.lastIndex == index) {
            Timber.d("Loading more movies")
            onActive {
                loadMoreData()
            }
        }
    }
}
