package com.example.movieappcompose.data.dataSources.db.models.tvShow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show_cast")
data class TvShowCastDb(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "show_id")
    val showId: Int,
    val character: String,
    val gender: Int,
    val name: String,
    val order: Int = -1,
    @ColumnInfo(name = "profile_path")
    val profilePath: String = "",
)
