package com.example.movieappcompose.data.dataSources.api.models

data class CastApi(
    val id: Int,
    val cast_id: Int,
    val credit_id: String,
    val character: String,
    val gender: Int,
    val name: String,
    val order: Int?,
    val profile_path: String?
)