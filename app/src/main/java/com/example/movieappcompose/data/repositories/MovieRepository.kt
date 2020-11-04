package com.example.movieappcompose.data.repositories

import android.util.Log
import com.example.movieappcompose.data.dataSources.api.MoviesApi
import com.example.movieappcompose.data.dataSources.api.models.CreditsApi
import com.example.movieappcompose.data.dataSources.api.models.GenreListApi
import com.example.movieappcompose.data.dataSources.api.models.MovieApi
import com.example.movieappcompose.data.dataSources.api.models.MoviesListApi
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.models.mappers.ApiResponseToMovie
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

class MovieRepository(private val api: MoviesApi) {

    fun getPopularMovies(page: Int): @NonNull Single<List<Movie>> {
        return getMovies(getPopularMoviesApi(page))
    }

    fun getUpcomingMovies(page: Int): @NonNull Single<List<Movie>> {
        return getMovies(getUpcomingMoviesApi(page))
    }

    private fun getMovies(type: Flowable<MovieApi>): @NonNull Single<List<Movie>> {
        return getGenres()
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
                                        .map { credits ->
                                            ApiResponseToMovie(movie, genreList, credits)
                                        }
                            }
                            .sorted { movie, movie2 -> movie2.popularity.compareTo(movie.popularity) }
                            .toList()
                }
                .onErrorReturn {
                    Log.e("MovieRepository", "getMovies -> ${it.stackTraceToString()}")
                    listOf()
                }
    }

    private fun getGenres(): Single<GenreListApi> = api.getGenres()

    private fun getCredits(movieId: Int): Single<CreditsApi> = api.getCredits(movieId = movieId)

    private fun getPopularMoviesApi(page: Int): Flowable<MovieApi> =
        api
                .getPopularMovies(page = page)
                .flattenAsFlowable(MoviesListApi::results)

    private fun getUpcomingMoviesApi(page: Int): Flowable<MovieApi> =
        api
                .getUpcomingMovies(page = page)
                .flattenAsFlowable(MoviesListApi::results)
}