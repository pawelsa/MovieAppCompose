package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew")
data class CrewDb(
    @PrimaryKey
    val id: Int,
    val movie_id: Int,
    val department: String,
    val gender: Int,
    val job: String,
    val name: String,
    @ColumnInfo(defaultValue = "")
    val profile_path: String? = ""
)
