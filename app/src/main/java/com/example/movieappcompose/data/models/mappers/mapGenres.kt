package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.GenreListApi
import com.example.movieappcompose.data.dataSources.db.models.GenreDb

fun GenreListApi.mapToDomain(): List<String> {
    return this.genres.map {
        it.name
    }
}

fun GenreListApi.mapToDb(): List<GenreDb> {
    return this.genres.map {
        GenreDb(
            genre_id = it.id,
            name = it.name
        )
    }
}