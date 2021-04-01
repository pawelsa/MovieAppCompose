package com.example.movieappcompose.data.dataSources.db.models.tvShow

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.movieappcompose.data.dataSources.db.models.movie.GenreDb
import com.example.movieappcompose.data.dataSources.db.models.movie.TvShowOrderDb

data class ShowToCrewAndCastRelationship(
    @Embedded val tvShowDb: TvShowDb,
    @Relation(
        parentColumn = "show_id",
        entityColumn = "show_id"
    )
    val crewList: List<TvShowCrewDb> = emptyList(),
    @Relation(
        parentColumn = "show_id",
        entityColumn = "show_id"
    )
    val castList: List<TvShowCastDb> = emptyList(),
    @Relation(
        parentColumn = "show_id",
        entityColumn = "genre_id",
        entity = GenreDb::class,
        associateBy = Junction(
            value = ShowGenreCrossRef::class,
            parentColumn = "show_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<GenreDb> = emptyList(),
    @Relation(
        parentColumn = "show_id",
        entityColumn = "show_id"
    )
    val orders: List<TvShowOrderDb> = emptyList(),
)