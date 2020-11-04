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
            original_title = movieApi.original_title,
            original_language = movieApi.original_language,
            posterPath = movieApi.poster_path,
            release_date = movieApi.release_date,
            vote_average = movieApi.vote_average,
            popularity = movieApi.popularity,
            genres = genreListApi.mapToDomain(),
            cast = creditsApi.cast.mapToDomain(),
            crew = creditsApi.crew.mapToDomain()
        )
    }