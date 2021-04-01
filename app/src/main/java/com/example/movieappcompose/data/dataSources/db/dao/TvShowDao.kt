package com.example.movieappcompose.data.dataSources.db.dao

import androidx.room.*
import com.example.movieappcompose.data.dataSources.db.models.movie.TvShowOrderDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class TvShowDao {

    @Transaction
    @Query("SELECT * FROM tv_show")
    abstract fun getAll(): Single<List<ShowToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM tv_show m JOIN show_order o ON m.show_id = o.show_id WHERE o.type = 1 ORDER BY o.`order` ASC")
    abstract fun getTopRatedShows(): Single<List<ShowToCrewAndCastRelationship>>

    // TODO: 13/11/2020 add sorting by order
    @Transaction
    @Query("SELECT m.* FROM tv_show m JOIN show_order o ON m.show_id = o.show_id WHERE o.type = 2 ORDER BY o.`order` ASC")
    abstract fun getPopularShows(): Single<List<ShowToCrewAndCastRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrder(orderDb: TvShowOrderDb): Completable

    fun insertAllOrders(listOfOrders: List<TvShowOrderDb>): Completable {
        return Completable.merge(listOfOrders.map { insertOrder(it) })
    }

    fun insertAll(listOfMovies: List<ShowToCrewAndCastRelationship>): Completable =
        Completable.merge(listOfMovies.map { insert(it) })

    fun insert(showToCrewAndCastRelationship: ShowToCrewAndCastRelationship): Completable =
        Completable.merge(listOf(
            _insertMovie(showToCrewAndCastRelationship.tvShowDb),
            _insertAllCast(showToCrewAndCastRelationship.castList),
            _insertAllCrew(showToCrewAndCastRelationship.crewList),
            _insertAllGenres(showToCrewAndCastRelationship.genres.map {
                ShowGenreCrossRef(
                    showId = showToCrewAndCastRelationship.tvShowDb.show_id,
                    genreId = it.genre_id
                )
            })
        ))

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertMovie(showDb: TvShowDb): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCast(castList: List<TvShowCastDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCrew(crewList: List<TvShowCrewDb>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllGenres(genreList: List<ShowGenreCrossRef>): Completable

    @Query("DELETE FROM show_order WHERE type = :showType")
    abstract fun deleteOrder(showType: Int): Completable

//    @Insert
//    abstract fun collectMovie(collectedDb: CollectedDb): Completable

//    @Query("DELETE FROM collected WHERE movieId = :movieId")
//    abstract fun uncollectMovie(movieId: Int): Completable

//    fun isMovieCollected(movieId: Int): Single<Boolean> = _isMovieCollected(movieId).map { it != 0 }

//    @Query("SELECT EXISTS (SELECT 1 FROM collected WHERE movieId = :movieId)")
//    abstract fun _isMovieCollected(movieId: Int): Single<Int>

}