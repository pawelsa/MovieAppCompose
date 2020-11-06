package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class CastDb(
    @PrimaryKey
    val cast_id: Int,
    val movie_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val order: Int?,
    val profile_path: String?
)