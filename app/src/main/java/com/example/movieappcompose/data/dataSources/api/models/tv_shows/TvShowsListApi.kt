package com.example.movieappcompose.data.dataSources.api.models.tv_shows

data class TvShowsListApi(
    val page: Int,
    val results: List<TvShowApi>,
    val total_pages: Int,
    val total_results: Int,
)