package com.example.movieappcompose.data.models.movie.mappers

import com.example.movieappcompose.data.dataSources.api.models.movie.GenreListApi
import com.example.movieappcompose.data.dataSources.db.models.movie.GenreDb
import com.example.movieappcompose.data.models.movie.Genre

fun GenreListApi.mapToDomain(): List<Genre> {
    return this.genres.map {
        Genre(
            genre_id = it.id,
            name = it.name
        )
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

fun List<Genre>.mapToDb(): List<GenreDb> {
    return this.map {
        GenreDb(
            genre_id = it.genre_id,
            name = it.name
        )
    }
}

fun List<GenreDb>.mapToDomain(): List<Genre> = map {
    Genre(
        genre_id = it.genre_id,
        name = it.name
    )
}
