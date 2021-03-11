package com.example.movieappcompose.data.dataSources.api

import com.example.movieappcompose.data.dataSources.api.models.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.GenreListApi
import com.example.movieappcompose.data.dataSources.api.models.MovieListApi
import com.example.movieappcompose.data.dataSources.api.models.ReviewListApi
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular?language=en-US")
    fun getPopularMovies(@Query("page") page: Int): Single<MovieListApi>

    @GET("movie/upcoming?language=en-US")
    fun getUpcomingMovies(@Query("page") page: Int): Single<MovieListApi>

    @GET("movie/{movieId}/credits")
    fun getCredits(@Path("movieId") movieId: Int): Single<CreditsApi>

    @GET("genre/movie/list?language=en-US")
    fun getGenres(): Single<GenreListApi>

    @GET("movie/{movieId}/reviews?language=en-US")
    fun getReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int = 1
    ): Single<ReviewListApi>

}