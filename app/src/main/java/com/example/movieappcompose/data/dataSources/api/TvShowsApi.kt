package com.example.movieappcompose.data.dataSources.api

import com.example.movieappcompose.data.dataSources.api.models.tv_shows.ShowCreditsApi
import com.example.movieappcompose.data.dataSources.api.models.tv_shows.TvShowsListApi
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApi {

    @GET("tv/top_rated?language=en-US")
    fun getTopRatedShows(@Query("page") page: Int): Single<TvShowsListApi>

    @GET("tv/popular?language=en-US")
    fun getPopularShows(@Query("page") page: Int): Single<TvShowsListApi>

    @GET("tv/{showId}/credits?language=en-US")
    fun getCredits(@Path("showId") showId: Int): Single<ShowCreditsApi>

}