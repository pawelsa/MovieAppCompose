package com.example.movieappcompose.screens.tv_shows

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.movieappcompose.data.models.tv_shows.TvShow
import timber.log.Timber


@Composable
fun TvShowsList(
    tvShows: List<TvShow>,
    loadMoreData: () -> Unit = {},
    onItemClick: (TvShow) -> Unit,
) {

    LazyColumn(content = {
        itemsIndexed(tvShows) { index, tvshow ->
            TvShowCard(
                tvShow = tvshow,
                onClick = {
                    onItemClick(tvshow)
                })
            if (tvShows.lastIndex == index) {
                Timber.d("Loading more shows")
                LaunchedEffect(index) {
                    loadMoreData()
                }
            }
        }
    })

}
