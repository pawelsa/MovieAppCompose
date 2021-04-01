package com.example.movieappcompose.data.dataSources.api.models.tv_shows

data class ShowCastApi(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int?,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
)