package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.GenreListApi
import com.example.movieappcompose.data.dataSources.api.models.MovieApi
import com.example.movieappcompose.data.dataSources.db.models.*
import com.example.movieappcompose.data.models.Movie

val ApiResponseToMovie: (MovieApi, GenreListApi, CreditsApi) -> Movie =
    { movieApi, genreListApi, creditsApi ->
        Movie(
            id = movieApi.id,
            title = movieApi.title,
            overview = movieApi.overview,
            originalTitle = movieApi.original_title,
            originalLanguage = movieApi.original_language,
            posterPath = movieApi.poster_path,
            releaseDate = movieApi.release_date,
            grade = movieApi.vote_average,
            popularity = movieApi.popularity,
            genres = movieApi.genre_ids.map { id ->
                genreListApi.genres.first { genre ->
                    genre.id == id
                }.name
            },
            cast = creditsApi.cast.mapToDomain(),
            crew = creditsApi.crew.mapToDomain()
        )
    }

val ApiResponseToMovieDb: (MovieApi, GenreListApi, CreditsApi) -> MovieToCrewAndCastRelationship =
    { movie, genres, credits ->
        MovieToCrewAndCastRelationship(
            movieDb = MovieDb(
                movie_id = movie.id,
                popularity = movie.popularity,
                release_date = movie.release_date,
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                title = movie.title,
                grade = movie.vote_average,
                poster_path = movie.poster_path
            ),
            castList = credits.cast.map { cast ->
                CastDb(
                    id = cast.id,
                    movie_id = movie.id,
                    name = cast.name,
                    character = cast.character,
                    gender = cast.gender,
                    order = cast.order,
                    profile_path = cast.profile_path
                )
            },
            crewList = credits.crew.map { crew ->
                CrewDb(
                    id = crew.id,
                    movie_id = movie.id,
                    profile_path = crew.profile_path,
                    gender = crew.gender,
                    name = crew.name,
                    job = crew.job,
                    department = crew.department
                )
            },
            genres = movie.genre_ids.map { genreId ->
                val genre = genres.genres.find { it.id == genreId }
                GenreDb(
                    name = genre?.name ?: "",
                    genre_id = genre?.id ?: -1
                )
            }
        )

    }
