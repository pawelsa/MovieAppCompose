package com.example.movieappcompose.data.dataSources.db.dao

import androidx.room.*
import com.example.movieappcompose.data.dataSources.db.models.movie.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    abstract fun getAll(): Single<List<MovieToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM movies m JOIN movie_order o ON m.movie_id = o.movie_id WHERE o.type = 1 ORDER BY o.`order` ASC")
    abstract fun getPopularMovies(): Single<List<MovieToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM movies m JOIN movie_order o ON m.movie_id = o.movie_id WHERE o.type = 2 ORDER BY o.`order` ASC")
    abstract fun getUpcomingMovies(): Single<List<MovieToCrewAndCastRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrder(orderDb: MovieOrderDb): Completable

    fun insertAllOrders(listOfOrders: List<MovieOrderDb>): Completable {
        return Completable.merge(listOfOrders.map { insertOrder(it) })
    }

    fun insertAll(listOfMovies: List<MovieToCrewAndCastRelationship>): Completable =
        Completable.merge(listOfMovies.map { insert(it) })

    fun insert(movieToCrewAndCastRelationship: MovieToCrewAndCastRelationship): Completable =
        Completable.merge(listOf(
            _insertMovie(movieToCrewAndCastRelationship.movieDb),
            _insertAllCast(movieToCrewAndCastRelationship.castList),
            _insertAllCrew(movieToCrewAndCastRelationship.crewList),
            _insertAllGenres(movieToCrewAndCastRelationship.genres.map {
                MovieGenreCrossRef(
                    movie_id = movieToCrewAndCastRelationship.movieDb.movie_id,
                    genre_id = it.genre_id
                )
            })
        ))

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertMovie(movieDb: MovieDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCast(castList: List<CastDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCrew(crewList: List<CrewDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllGenres(genreList: List<MovieGenreCrossRef>): Completable

    @Query("DELETE FROM movie_order WHERE movie_order.type = :movieType")
    abstract fun deleteOrder(movieType: Int): Completable

    @Insert
    abstract fun collectMovie(collectedDb: CollectedDb): Completable

    @Query("DELETE FROM collected WHERE movieId = :movieId")
    abstract fun uncollectMovie(movieId: Int): Completable

    fun isMovieCollected(movieId: Int): Single<Boolean> = _isMovieCollected(movieId).map { it != 0 }

    @Query("SELECT EXISTS (SELECT 1 FROM collected WHERE movieId = :movieId)")
    abstract fun _isMovieCollected(movieId: Int): Single<Int>

}