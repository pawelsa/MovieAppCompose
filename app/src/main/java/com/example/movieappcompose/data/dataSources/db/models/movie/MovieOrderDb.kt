package com.example.movieappcompose.data.dataSources.db.models.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

const val MOVIE_POPULAR = 1
const val MOVIE_UPCOMING = 2

@Entity(tableName = "movie_order")
data class MovieOrderDb(
    val order: Int,
    val type: Int,
    val movie_id: Int,
    @PrimaryKey(autoGenerate = true)
    val order_id: Int = 0,
)

@Entity(tableName = "show_order")
data class TvShowOrderDb(
    val order: Int,
    val type: Int,
    val show_id: Int,
    @PrimaryKey(autoGenerate = true)
    val order_id: Int = 0,
)