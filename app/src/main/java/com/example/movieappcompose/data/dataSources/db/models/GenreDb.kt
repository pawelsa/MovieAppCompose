package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreDb(
    @PrimaryKey
    val genre_id: Int,
    val name: String
)
