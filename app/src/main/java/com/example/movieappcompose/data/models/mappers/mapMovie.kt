package com.example.movieappcompose.data.models.mappers

import com.example.movieappcompose.data.dataSources.api.models.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.GenreListApi
import com.example.movieappcompose.data.dataSources.api.models.MovieApi
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