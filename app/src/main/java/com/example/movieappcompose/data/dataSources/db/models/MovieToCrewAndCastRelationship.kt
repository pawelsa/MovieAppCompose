package com.example.movieappcompose.data.dataSources.db.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieToCrewAndCastRelationship(
    @Embedded val movieDb: MovieDb,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val crewList: List<CrewDb>,
    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val castList: List<CastDb>
)