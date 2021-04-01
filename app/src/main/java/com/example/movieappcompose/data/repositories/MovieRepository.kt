package com.example.movieappcompose.data.repositories

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.movie.ReviewApi
import com.example.movieappcompose.data.dataSources.api.models.movie.ReviewListApi
import com.example.movieappcompose.data.dataSources.db.dao.MovieDao
import com.example.movieappcompose.data.dataSources.db.models.movie.CollectedDb
import com.example.movieappcompose.data.models.movie.Discussion
import com.example.movieappcompose.data.models.movie.Movie
import com.example.movieappcompose.data.models.movie.Review
import com.example.movieappcompose.data.models.movie.mappers.mapToDomain
import com.example.movieappcompose.data.persistence.PersistMovieLists
import com.example.movieappcompose.data.persistence.PersistPopularMoviesList
import com.example.movieappcompose.data.persistence.PersistUpcomingMoviesList
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class MovieRepository(
    private val api: MoviesApi,
    private val persistPopularMovies: PersistPopularMoviesList,
    private val persistUpcomingMovies: PersistUpcomingMoviesList,
    private val movieDao: MovieDao
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

    // TODO: 21.03.2021 getMovieDiscussion method should be implemented
    fun getMovieDiscussion(movieId: Int): Single<List<Discussion>> = Single.just(emptyList())

    fun isMovieCollected(movieId: Int) = movieDao.isMovieCollected(movieId = movieId)

    fun changeMovieCollectStatus(movieId: Int, isCollected: Boolean): Single<Boolean> {
        return if (isCollected) {
            movieDao.uncollectMovie(movieId = movieId)
        } else {
            val collectedDb = CollectedDb(movieId = movieId, true)
            movieDao.collectMovie(collectedDb = collectedDb)
        }
            .andThen(Single.just(!isCollected))
    }

}