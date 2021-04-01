package com.example.movieappcompose.data.dataSources.db.models.movie

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
This class is used for getting data from database
 * */
data class MovieToCrewAndCastRelationship(
    @Embedded val movieDb: MovieDb,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val crewList: List<CrewDb> = emptyList(),
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val castList: List<CastDb> = emptyList(),
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "genre_id",
        entity = GenreDb::class,
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<GenreDb> = emptyList(),
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val orders: List<MovieOrderDb> = emptyList()
)
