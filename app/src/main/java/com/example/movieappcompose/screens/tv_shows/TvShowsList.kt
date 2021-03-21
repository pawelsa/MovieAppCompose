package com.example.movieappcompose.screens.tv_shows

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.movieappcompose.data.models.Movie
import timber.log.Timber


@Composable
fun TvShowsList(
    movieList: List<Movie>,
    loadMoreData: () -> Unit = {},
    onItemClick: (Movie) -> Unit
) {

    LazyColumn(content = {
        itemsIndexed(movieList) { index, movie ->
            MovieCard(
                movie = movie,
                onClick = {
                    onItemClick(movie)
                })
            if (movieList.lastIndex == index) {
                Timber.d("Loading more shows")
                LaunchedEffect(index) {
                    loadMoreData()
                }
            }
        }
    })

}
