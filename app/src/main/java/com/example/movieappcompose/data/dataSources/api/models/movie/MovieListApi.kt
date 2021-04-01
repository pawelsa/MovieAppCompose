package com.example.movieappcompose.data.dataSources.api.models.movie

data class MovieListApi(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieApi>
)