package com.example.movieappcompose.data.models.tv_shows.mappers

import com.example.movieappcompose.data.dataSources.api.models.tv_shows.ShowCastApi
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCastDb
import com.example.movieappcompose.data.models.tv_shows.ShowCast

val ApiCastToDomain: (showCastApi: ShowCastApi) -> ShowCast = {
    ShowCast(
        profilePath = it.profile_path ?: "",
        name = it.name,
        character = it.character,
        order = it.order ?: -1,
    )
}

fun List<ShowCastApi>.mapToDomain() = this.map {
    ShowCast(
        character = it.character,
        name = it.name,
        order = it.order ?: -1,
        profilePath = it.profile_path ?: "",
    )
}

@JvmName("mapToDomainCastDb")
fun List<TvShowCastDb>.mapToDomain() = map {
    ShowCast(
        name = it.name,
        order = it.order,
        character = it.character,
        profilePath = it.profilePath
    )
}