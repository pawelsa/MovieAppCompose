package com.example.movieappcompose.data.dataSources.api.models.movie

data class CreditsApi(
    val id: Int,
    val cast: List<CastApi>,
    val crew: List<CrewApi>
)