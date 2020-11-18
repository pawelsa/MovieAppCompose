package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cast")
data class CastDb(
    @PrimaryKey
    val id: Int,
    val movie_id: Int,
    @ColumnInfo(defaultValue = "")
    val character: String = "",
    val gender: Int,
    val name: String,
    @ColumnInfo(defaultValue = "-1")
    val order: Int = -1,
    @ColumnInfo(defaultValue = "")
    val profile_path: String = ""
)
