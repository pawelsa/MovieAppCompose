package com.example.movieappcompose.data.models.movie.mappers

import com.example.movieappcompose.data.dataSources.api.models.movie.CastApi
import com.example.movieappcompose.data.dataSources.db.models.movie.CastDb
import com.example.movieappcompose.data.models.movie.Cast

fun List<CastApi>.mapToDomain() = this.map {
    Cast(
        character = it.character ?: "",
        name = it.name,
        order = it.order ?: -1,
        profilePicture = it.profile_path ?: ""
    )
}

@JvmName("mapToDomainCastDb")
fun List<CastDb>.mapToDomain() = map {
    Cast(
        name = it.name,
        order = it.order,
        character = it.character,
        profilePicture = it.profile_path
    )
}