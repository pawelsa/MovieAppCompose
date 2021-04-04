package com.example.movieappcompose.data.dataSources.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieappcompose.data.dataSources.db.dao.GenreDao
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.dataSources.db.dao.TvShowDao
import com.example.movieappcompose.data.dataSources.db.models.movie.*
import com.example.movieappcompose.data.dataSources.db.models.tvShow.ShowGenreCrossRef
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCastDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCrewDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowDb

@Database(
    entities = [MovieDb::class, CastDb::class, CrewDb::class, GenreDb::class, MovieGenreCrossRef::class, MovieOrderDb::class, CollectedMovieDb::class, TvShowDb::class, TvShowCrewDb::class, TvShowCastDb::class, ShowGenreCrossRef::class, TvShowOrderDb::class, CollectedTvShowDb::class],
    version = 1
)
abstract class MoviesDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun showDao(): TvShowDao

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

