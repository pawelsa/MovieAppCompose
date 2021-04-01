package com.example.movieappcompose.data.dataSources.db.models.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew")
data class CrewDb(
    @PrimaryKey
    val id: Int,
    val job: String,
    val name: String,
    @ColumnInfo(name = "profile_path", defaultValue = "")
    val profilePath: String? = "",
    val gender: Int,
    val department: String,
    val movie_id: Int,
)
