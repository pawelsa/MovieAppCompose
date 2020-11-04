package com.example.movieappcompose.data.dataSources.api.models

data class CreditsApi(
    val id: Int,
    val cast: List<CastApi>,
    val crew: List<CrewApi>
)