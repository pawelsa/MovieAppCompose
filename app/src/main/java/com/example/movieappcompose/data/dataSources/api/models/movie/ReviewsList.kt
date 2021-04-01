package com.example.movieappcompose.data.dataSources.api.models.movie

data class ReviewListApi(
    val id: Int,
    val page: Int,
    val results: List<ReviewApi>,
    val total_pages: Int,
    val total_results: Int
)