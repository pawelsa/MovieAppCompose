package com.example.movieappcompose.data.models

data class DetailedMovie(
    val movie: Movie,
    val reviews: List<Review> = emptyList(),
    val discussion: List<Discussion> = emptyList(),
    val isCollected: Boolean = false
)