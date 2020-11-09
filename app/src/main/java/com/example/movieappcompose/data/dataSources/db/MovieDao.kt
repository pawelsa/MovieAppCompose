package com.example.movieappcompose.data.dataSources.db

import androidx.room.*
import com.example.movieappcompose.data.dataSources.db.models.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import timber.log.Timber

@Dao
abstract class MovieDao {

    @Transaction
    @Query("SELECT * FROM movies")
    abstract fun getAll(): List<MovieToCrewAndCastRelationship>

    fun insertAll(movieDbList: List<MovieToCrewAndCastRelationship>): Completable {
        return Flowable
                .fromIterable(movieDbList)
                .flatMapCompletable {
                    insert(it)
                }
    }

    fun insert(movieToCrewAndCastRelationship: MovieToCrewAndCastRelationship): Completable {
        return Completable
                .fromCallable {
                    Timber.d("Insert movie")
                    _insertMovie(movieToCrewAndCastRelationship.movieDb)
                    Timber.d("Insert all cast")
                    _insertAllCast(movieToCrewAndCastRelationship.castList)
                    Timber.d("Insert all crew")
                    _insertAllCrew(movieToCrewAndCastRelationship.crewList)
                    Timber.d("Insert all genres relations")
                    _insertAllGenres(movieToCrewAndCastRelationship.genres.map {
                        MovieGenreCrossRef(
                            movie_id = movieToCrewAndCastRelationship.movieDb.movie_id,
                            genre_id = it.genre_id
                        )
                    })
                }
                .doOnComplete {
                    Timber.d("Insert ended")
                }
                .doOnError {
                    Timber.d(it, "Insert failed")
                }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertMovie(movieDb: MovieDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCast(castList: List<CastDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllCrew(crewList: List<CrewDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun _insertAllGenres(genreList: List<MovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertGenres(genres: List<GenreDb>)
}