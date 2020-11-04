package com.example.movieappcompose.data.dataSources.api.models

data class MoviesListApi(
	val page: Int,
	val total_results: Int,
	val total_pages: Int,
	val results: List<MovieApi>
)