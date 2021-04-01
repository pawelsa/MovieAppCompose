package com.example.movieappcompose.data.models.movie.mappers

import com.example.movieappcompose.data.dataSources.api.models.movie.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.movie.MovieApi
import com.example.movieappcompose.data.dataSources.db.models.movie.*
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.data.models.movie.Movie

val ApiResponseToMovie: (MovieApi, List<Genre>, CreditsApi) -> Movie =
    { movieApi, genreList, creditsApi ->
        Movie(
            id = movieApi.id,
            title = movieApi.title,
            overview = movieApi.overview,
            originalTitle = movieApi.original_title,
            originalLanguage = movieApi.original_language,
            posterPath = movieApi.poster_path ?: "",
            releaseDate = movieApi.release_date ?: "",
            grade = movieApi.vote_average,
            popularity = movieApi.popularity,
            genres = genreList.filter {
                movieApi.genre_ids.contains(it.genre_id)
            },
            cast = creditsApi.cast.mapToDomain(),
            crew = creditsApi.crew.mapToDomain()
        )
    }

val ApiResponseToMovieDb: (MovieApi, List<Genre>, CreditsApi, List<MovieOrderDb>) -> MovieToCrewAndCastRelationship =
    { movie, genres, credits, order ->
        MovieToCrewAndCastRelationship(
            movieDb = MovieDb(
                movie_id = movie.id,
                popularity = movie.popularity,
                release_date = movie.release_date ?: "",
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                title = movie.title,
                grade = movie.vote_average,
                poster_path = movie.poster_path ?: ""
            ),
            castList = credits.cast.map { cast ->
                CastDb(
                    id = cast.id,
                    movie_id = movie.id,
                    name = cast.name,
                    character = cast.character ?: "",
                    gender = cast.gender,
                    order = cast.order ?: -1,
                    profile_path = cast.profile_path ?: ""
                )
            },
            crewList = credits.crew.map { crew ->
                CrewDb(
                    id = crew.id,
                    movie_id = movie.id,
                    profilePath = crew.profile_path ?: "",
                    gender = crew.gender,
                    name = crew.name,
                    job = crew.job,
                    department = crew.department
                )
            },
            genres = movie.genre_ids.map { genreId ->
                val genre = genres.find { it.genre_id == genreId }
                GenreDb(
                    name = genre?.name ?: "",
                    genre_id = genre?.genre_id ?: -1
                )
            },
            orders = order
        )

    }

fun List<MovieToCrewAndCastRelationship>.mapToDomain() = map(DbResponseToDomain)

val DbResponseToDomain: (MovieToCrewAndCastRelationship) -> Movie = { movie ->
    Movie(
        id = movie.movieDb.movie_id,
        posterPath = movie.movieDb.poster_path,
        title = movie.movieDb.title,
        originalLanguage = movie.movieDb.original_language,
        originalTitle = movie.movieDb.original_title,
        grade = movie.movieDb.grade,
        overview = movie.movieDb.overview,
        releaseDate = movie.movieDb.release_date,
        popularity = movie.movieDb.popularity,
        genres = movie.genres.mapToDomain(),
        cast = movie.castList.mapToDomain(),
        crew = movie.crewList.mapToDomain()
    )
}
