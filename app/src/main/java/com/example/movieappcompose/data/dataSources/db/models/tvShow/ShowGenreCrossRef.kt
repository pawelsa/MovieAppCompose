package com.example.movieappcompose.data.dataSources.db.models.tvShow

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["show_id", "genre_id"])
data class ShowGenreCrossRef(
    @ColumnInfo(name = "show_id")
    val showId: Int,
    @ColumnInfo(name = "genre_id")
    val genreId: Int,
)
