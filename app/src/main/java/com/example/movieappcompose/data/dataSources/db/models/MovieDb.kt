package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDb(
    @PrimaryKey val movie_id: Int,
    @ColumnInfo(defaultValue = "")
    val poster_path: String = "",
    val title: String,
    val original_language: String,
    val original_title: String,
    val grade: Double,
    @ColumnInfo(defaultValue = "")
    val overview: String = "",
    val release_date: String,
    val popularity: Double,
)