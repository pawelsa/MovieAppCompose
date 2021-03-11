package com.example.movieappcompose.data.models

data class DetailedMovie(
    val movie: Movie,
    val reviews: List<Review>,
    val discussion: List<Discussion>
)