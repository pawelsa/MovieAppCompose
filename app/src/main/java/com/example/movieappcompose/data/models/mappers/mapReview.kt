package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.ReviewApi
import com.example.movieappcompose.data.models.Review

fun List<ReviewApi>.mapToDomain() = this.map {
    Review(
        author = it.author,
        content = it.content,
    )
}