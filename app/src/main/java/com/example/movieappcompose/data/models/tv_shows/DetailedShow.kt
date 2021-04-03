package com.example.movieappcompose.data.models.tv_shows

import com.example.movieappcompose.data.models.movie.Discussion
import com.example.movieappcompose.data.models.movie.Review

data class DetailedShow(
    val tvShow: TvShow,
    val reviews: List<Review> = emptyList(),
    val discussion: List<Discussion> = emptyList(),
    val isCollected: Boolean = false
)