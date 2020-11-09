package com.example.movieappcompose.data.repositories

import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.*
import com.example.movieappcompose.data.dataSources.db.MovieDao
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Discussion
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.models.Review
import com.example.movieappcompose.data.models.mappers.ApiResponseToMovie
import com.example.movieappcompose.data.models.mappers.ApiResponseToMovieDb
import com.example.movieappcompose.data.models.mappers.mapToDb
import com.example.movieappcompose.data.models.mappers.mapToDomain
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber

class MovieRepository(private val api: MoviesApi, private val movieDao: MovieDao) {

    fun getPopularMovies(page: Int): @NonNull Single<List<Movie>> {
        return getMovies(getPopularMoviesApi(page))
    }

    fun getUpcomingMovies(page: Int): @NonNull Single<List<Movie>> {
        return getMovies(getUpcomingMoviesApi(page))
    }

    // TODO: 05/11/2020 this should be done and connected to detailMovieViewModel
    fun getDetailedMovie(movieId: Int): Single<DetailedMovie> =
        throw NotImplementedError("getDetailedMovie")

    // TODO: 05/11/2020 should be private
    fun getMovieReviews(movieId: Int): Single<List<Review>> =
        api
                .getReviews(movieId = movieId)
                .map(ReviewListApi::results)
                .map(List<ReviewApi>::mapToDomain)

    private fun getMovie(movieId: Int): Single<Movie> =
        throw NotImplementedError("getMovie method was not implemented")

    private fun getMovieDiscussion(movieId: Int): Single<List<Discussion>> =
        throw NotImplementedError("getMovieDiscussion method was not implemented")


    private fun getMovies(type: Flowable<MovieApi>): @NonNull Single<List<Movie>> {
        return getGenres()
                .flatMap {
                    Single.fromCallable {
                        movieDao.insertGenres(it.mapToDb())
                        it
                    }
                }
                .concatMap { genreList ->
                    type
                            // TODO: 04/11/2020 test how parallel improves the results,
                            //  because sorting list might be more demanding

                            // TODO: 04/11/2020 test how much improvement will be gained if
                            //  sequential would be moved after flatMapSingle, so that getCredits,
                            //  still will be done parallel
                            .parallel()
                            .sequential()
                            .flatMapSingle { movie ->
                                getCredits(movie.id)
                                        .flatMap { credits ->
                                            movieDao
                                                    .insert(
                                                        ApiResponseToMovieDb(
                                                            movie,
                                                            genreList,
                                                            credits
                                                        )
                                                    )
                                                    .toSingle { }
                                                    .map {
                                                        ApiResponseToMovie(
                                                            movie,
                                                            genreList,
                                                            credits
                                                        )
                                                    }
                                        }
                            }
                            .sorted { movie, movie2 -> movie2.popularity.compareTo(movie.popularity) }
                            .toList()
                }
                .onErrorReturn {
                    Timber.e("getMovies -> ${it.stackTraceToString()}")
                    listOf()
                }
    }

    private fun getGenres(): Single<GenreListApi> = api.getGenres()

    private fun getCredits(movieId: Int): Single<CreditsApi> = api.getCredits(movieId = movieId)

    private fun getPopularMoviesApi(page: Int): Flowable<MovieApi> =
        api
                .getPopularMovies(page = page)
                .flattenAsFlowable(MovieListApi::results)

    private fun getUpcomingMoviesApi(page: Int): Flowable<MovieApi> =
        api
                .getUpcomingMovies(page = page)
                .flattenAsFlowable(MovieListApi::results)
}