package com.example.movieappcompose.data.repositories

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.ReviewApi
import com.example.movieappcompose.data.dataSources.api.models.ReviewListApi
import com.example.movieappcompose.data.models.Discussion
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.models.Review
import com.example.movieappcompose.data.models.mappers.mapToDomain
import com.example.movieappcompose.data.persistence.PersistMovieLists
import com.example.movieappcompose.data.persistence.PersistPopularMoviesList
import com.example.movieappcompose.data.persistence.PersistUpcomingMoviesList
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class MovieRepository(
    private val api: MoviesApi,
    private val persistPopularMovies: PersistPopularMoviesList,
    private val persistUpcomingMovies: PersistUpcomingMoviesList
) {

    fun getPopularMovies(page: Int): @NonNull Observable<List<Movie>> {
        return persistPopularMovies.get(PersistMovieLists.Param(page))
    }

    fun getUpcomingMovies(page: Int): @NonNull Observable<List<Movie>> {
        return persistUpcomingMovies.get(PersistMovieLists.Param(page))
    }

    fun getMovieReviews(movieId: Int): Single<List<Review>> =
        api
                .getReviews(movieId = movieId)
                .map(ReviewListApi::results)
                .map(List<ReviewApi>::mapToDomain)

    private fun getMovie(movieId: Int): Single<Movie> =
        throw NotImplementedError("getMovie method was not implemented")

    private fun getMovieDiscussion(movieId: Int): Single<List<Discussion>> =
        throw NotImplementedError("getMovieDiscussion method was not implemented")

}