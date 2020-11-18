package com.example.movieappcompose.data.dataSources.db.dao

import androidx.room.*
import com.example.movieappcompose.data.dataSources.db.models.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    abstract fun getAll(): Flow<List<MovieToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM movies m JOIN movie_order o ON m.movie_id = o.movie_id WHERE o.type = 1 ORDER BY o.`order` ASC")
    abstract fun getPopularMovies(): Flow<List<MovieToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM movies m JOIN movie_order o ON m.movie_id = o.movie_id WHERE o.type = 2 ORDER BY o.`order` ASC")
    abstract fun getUpcomingMovies(): Flow<List<MovieToCrewAndCastRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrder(orderDb: MovieOrderDb)

    fun insert(movieToCrewAndCastRelationship: MovieToCrewAndCastRelationship) {
        _insertMovie(movieToCrewAndCastRelationship.movieDb)
        _insertAllCast(movieToCrewAndCastRelationship.castList)
        _insertAllCrew(movieToCrewAndCastRelationship.crewList)
        _insertAllGenres(movieToCrewAndCastRelationship.genres.map {
            MovieGenreCrossRef(
                movie_id = movieToCrewAndCastRelationship.movieDb.movie_id,
                genre_id = it.genre_id
            )
        })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertMovie(movieDb: MovieDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCast(castList: List<CastDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCrew(crewList: List<CrewDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllGenres(genreList: List<MovieGenreCrossRef>)

    @Query("DELETE FROM movie_order WHERE movie_order.type = :movieType")
    abstract fun deleteOrder(movieType: Int)
}