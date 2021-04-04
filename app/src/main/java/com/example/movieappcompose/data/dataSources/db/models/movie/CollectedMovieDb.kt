package com.example.movieappcompose.data.dataSources.db.models.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collected_movie")
data class CollectedMovieDb(
    @PrimaryKey @ColumnInfo(name = "id") val movieId: Int,
    @ColumnInfo(name = "is_collected") val isCollected: Boolean
)

@Entity(tableName = "collected_shows")
data class CollectedTvShowDb(
    @PrimaryKey @ColumnInfo(name = "id") val tvShowId: Int,
    @ColumnInfo(name = "is_collected") val isCollected: Boolean
)
