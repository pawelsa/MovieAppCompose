package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.Entity

@Entity(primaryKeys = ["movie_id", "genre_id"])
data class MovieGenreCrossRef(
    val movie_id: Int,
    val genre_id: Int
)
