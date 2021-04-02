package com.example.movieappcompose.data.models.tv_shows.mappers

import com.example.movieappcompose.data.dataSources.api.models.tv_shows.ShowCreditsApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.TvShowApi
import com.example.movieappcompose.data.dataSources.db.models.movie.GenreDb
import com.example.movieappcompose.data.dataSources.db.models.movie.TvShowOrderDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.ShowToCrewAndCastRelationship
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCastDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowCrewDb
import com.example.movieappcompose.data.dataSources.db.models.tvShow.TvShowDb
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.data.models.movie.mappers.mapToDomain
import com.example.movieappcompose.data.models.tv_shows.TvShow

/*val ApiShowToDomain: (api: TvShowApi, genreList: List<Genre>, creditsApi: ShowCreditsApi, order: List<TvShowOrderDb>) -> TvShow =
    { api, genreList, credits, order ->
        throw Exception("Not implemented")
        TvShow(
            id = api.id,
            title = api.name,
            backdropPath = api.backdrop_path ?: "",
            releaseDate = api.first_air_date ?: "",
            genres = api.genre_ids.map { genreId ->
                genreList.first { it.genre_id == genreId }
            },
            originCountry = api.origin_country,
            originalLanguage = api.original_language,
            originalTitle = api.original_name,
            overview = api.overview,
            popularity = api.popularity,
            posterPath = api.poster_path,
            grade = api.vote_average,
            cast = credits.cast.map(ApiCastToDomain),
            crew = credits.crew.map(ApiCrewToDomain),
        )
    }*/

val ApiShowToDb: (api: TvShowApi, genreList: List<Genre>, creditsApi: ShowCreditsApi, order: List<TvShowOrderDb>) -> ShowToCrewAndCastRelationship =
    { api, genreList, creditsApi, order ->
        ShowToCrewAndCastRelationship(
            tvShowDb = TvShowDb(
                show_id = api.id,
                popularity = api.popularity,
                name = api.name,
                overview = api.overview,
                grade = api.vote_average,
                originalLanguage = api.original_language,
                originalTitle = api.original_name,
                posterPath = api.poster_path,
                releaseDate = api.first_air_date ?: "",
                backdropPath = api.backdrop_path ?: ""
            ),
            castList = creditsApi.cast.map {
                TvShowCastDb(
                    id = it.id,
                    showId = api.id,
                    name = it.name,
                    order = it.order ?: -1,
                    character = it.character,
                    gender = it.gender,
                    profilePath = it.profile_path ?: "",
                )
            },
            crewList = creditsApi.crew.map {
                TvShowCrewDb(
                    id = it.id,
                    showId = api.id,
                    name = it.name,
                    job = it.job,
                    gender = it.gender,
                    department = it.department,
                    profilePath = it.profile_path ?: "",
                )
            },
            genres = api.genre_ids.map { genreId ->
                val genre = genreList.find { it.genre_id == genreId }
                GenreDb(
                    name = genre?.name ?: "",
                    genre_id = genre?.genre_id ?: -1
                )
            },
            orders = order
        )
    }

fun List<ShowToCrewAndCastRelationship>.mapToDomain() = map(DbResponseToDomain)

val DbResponseToDomain: (ShowToCrewAndCastRelationship) -> TvShow = { show ->
    TvShow(
        id = show.tvShowDb.show_id,
        posterPath = show.tvShowDb.posterPath,
        title = show.tvShowDb.name,
        originalLanguage = show.tvShowDb.originalLanguage,
        originalTitle = show.tvShowDb.originalTitle,
        grade = show.tvShowDb.grade,
        overview = show.tvShowDb.overview,
        releaseDate = show.tvShowDb.releaseDate,
        popularity = show.tvShowDb.popularity,
        genres = show.genres.mapToDomain(),
        cast = show.castList.mapToDomain(),
        crew = show.crewList.mapToDomain(),
        backdropPath = show.tvShowDb.backdropPath,
        originCountry = emptyList()
    )
}