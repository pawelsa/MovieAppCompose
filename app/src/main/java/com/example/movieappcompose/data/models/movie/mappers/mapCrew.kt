package com.example.movieappcompose.data.models.movie.mappers

import com.example.movieappcompose.data.dataSources.api.models.movie.CrewApi
import com.example.movieappcompose.data.dataSources.db.models.movie.CrewDb
import com.example.movieappcompose.data.models.movie.Crew

fun List<CrewApi>.mapToDomain() = this.map {
    Crew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePicture = it.profile_path ?: ""
    )
}

@JvmName("mapToDomainCrewDb")
fun List<CrewDb>.mapToDomain() = this.map {
    Crew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePicture = it.profilePath ?: ""
    )
}
