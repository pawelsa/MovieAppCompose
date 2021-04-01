package com.example.movieappcompose.data.dataSources.api.models.tv_shows

data class ShowCreditsApi(
    val cast: List<ShowCastApi>,
    val crew: List<ShowCrewApi>,
    val id: Int,
)