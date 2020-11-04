package com.example.movieappcompose.data.models

data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String,
    val original_language: String,
    val original_title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    val popularity: Double,
    val genres: List<String>,
    val cast: List<Cast>,
    val crew: List<Crew>
)