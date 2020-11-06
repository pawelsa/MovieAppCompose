package com.example.movieappcompose.data.dataSources.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieappcompose.data.dataSources.db.models.MovieDb

@Database(entities = arrayOf(MovieDb::class), version = 1)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        fun createInstance(context: Context): MoviesDB {
            return Room
                    .databaseBuilder(
                        context,
                        MoviesDB::class.java,
                        "movies_db"
                    )
                    .build()
        }
    }
}

