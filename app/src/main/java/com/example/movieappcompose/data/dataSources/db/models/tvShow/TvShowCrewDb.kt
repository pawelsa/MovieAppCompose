package com.example.movieappcompose.data.dataSources.db.models.tvShow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "show_crew")
data class TvShowCrewDb(
    @PrimaryKey
    val id: Int,
    val job: String,
    val name: String,
    @ColumnInfo(name = "profile_path", defaultValue = "")
    val profilePath: String = "",
    val gender: Int,
    val department: String,
    @ColumnInfo(name = "show_id")
    val showId: Int,
)
