package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.GenreListApi

fun GenreListApi.mapToDomain(): List<String> {
    return this.genres.map {
        it.name
    }
}