package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collected")
data class CollectedDb(@PrimaryKey val movieId: Int, val isCollected: Boolean)
