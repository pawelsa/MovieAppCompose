package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.CrewApi
import com.example.movieappcompose.data.models.Crew

fun List<CrewApi>.mapToDomain() = this.map {
    Crew(
        name = it.name,
        department = it.department,
        job = it.job,
        profilePicture = it.profile_path ?: ""
    )
}
