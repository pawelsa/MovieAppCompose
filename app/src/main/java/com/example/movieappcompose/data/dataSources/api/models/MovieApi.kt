package com.example.movieappcompose.data.dataSources.api.models

data class MovieApi(
    val id: Int,
    val poster_path: String?,
    val title: String,
    val original_language: String,
    val original_title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String?,
    val genre_ids: List<Int>,
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val adult: Boolean,
    val backdrop_path: String?,
)