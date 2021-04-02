package com.example.movieappcompose.data.dataSources.db.models.tvShow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show")
data class TvShowDb(
    @PrimaryKey
    val show_id: Int,
    val name: String,
    @ColumnInfo(name = "posterPath", defaultValue = "")
    val posterPath: String = "",
    @ColumnInfo(name = "backdrop_path", defaultValue = "")
    val backdropPath: String = "",
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    val grade: Double,
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val popularity: Double,
)
