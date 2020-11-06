package com.example.movieappcompose.data.dataSources.db

import androidx.room.*
import com.example.movieappcompose.data.dataSources.db.models.MovieToCrewAndCastRelationship

@Dao
interface MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieToCrewAndCastRelationship>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieDbList: List<MovieToCrewAndCastRelationship>)
}