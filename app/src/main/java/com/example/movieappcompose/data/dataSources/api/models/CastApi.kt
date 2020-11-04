package com.example.movieappcompose.data.dataSources.api.models

data class CastApi(
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int?,
    val profile_path: String?
)