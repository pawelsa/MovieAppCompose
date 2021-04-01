package com.example.movieappcompose.data.models.movie.mappers

import com.example.movieappcompose.data.dataSources.api.models.movie.ReviewApi
import com.example.movieappcompose.data.models.movie.Review

fun List<ReviewApi>.mapToDomain() = this.map {
    Review(
        author = it.author,
        content = it.content,
    )
}