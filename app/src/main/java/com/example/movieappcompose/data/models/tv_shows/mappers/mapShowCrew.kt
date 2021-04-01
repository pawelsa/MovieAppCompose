package com.example.movieappcompose.data.models.tv_shows.mappers

import com.example.movieappcompose.data.dataSources.api.models.tv_shows.ShowCrewApi
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCrewDb
import com.example.movieappcompose.data.models.tv_shows.ShowCrew

val ApiCrewToDomain: (showCrewApi: ShowCrewApi) -> ShowCrew = {
    ShowCrew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePath = it.profile_path ?: ""
    )
}

fun List<ShowCrewApi>.mapToDomain() = this.map {
    ShowCrew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePath = it.profile_path ?: ""
    )
}

@JvmName("mapToDomainCrewDb")
fun List<TvShowCrewDb>.mapToDomain() = this.map {
    ShowCrew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePath = it.profilePath
    )
}