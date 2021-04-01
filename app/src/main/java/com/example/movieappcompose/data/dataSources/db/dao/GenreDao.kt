package com.example.movieappcompose.data.dataSources.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappcompose.data.dataSources.db.models.movie.GenreDb
import io.reactivex.rxjava3.core.Single

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getAll(): Single<List<GenreDb>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(genres: List<GenreDb>)

}