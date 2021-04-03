package com.example.movieappcompose.data.repositories

import com.example.movieappcompose.data.dataSources.api.TvShowsApi
import com.example.movieappcompose.data.dataSources.db.dao.TvShowDao
import com.example.movieappcompose.data.models.movie.Discussion
import com.example.movieappcompose.data.models.movie.Movie
import com.example.movieappcompose.data.models.movie.Review
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.persistence.PersistPopularShowsList
import com.example.movieappcompose.data.persistence.PersistTopRatedShowsList
import com.example.movieappcompose.data.persistence.PersistTvShowLists
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


class TvShowRepository(
    private val api: TvShowsApi,
    private val persistPopularShows: PersistPopularShowsList,
    private val persistTopRatedShows: PersistTopRatedShowsList,
    private val tvShowDao: TvShowDao
) {

    fun getPopularShows(page: Int): @NonNull Observable<List<TvShow>> {
        return persistPopularShows.get(PersistTvShowLists.Param(page))
    }

    fun getTopRatedShows(page: Int): @NonNull Observable<List<TvShow>> {
        return persistTopRatedShows.get(PersistTvShowLists.Param(page))
    }

    fun getTvShowReviews(showId: Int): Single<List<Review>> = Single.just(emptyList())
    /*api
        .getReviews(movieId = movieId)
        .map(ReviewListApi::results)
        .map(List<ReviewApi>::mapToDomain)*/

    private fun getTvShow(tvShowId: Int): Single<Movie> =
        throw NotImplementedError("getMovie method was not implemented")

    // TODO: 21.03.2021 getMovieDiscussion method should be implemented
    fun getTvShowDiscussion(tvShowId: Int): Single<List<Discussion>> = Single.just(emptyList())

    fun isShowCollected(tvShowId: Int) =
        Single.just(false) //tvShowDao.isMovieCollected(movieId = movieId)

/*    fun changeMovieCollectStatus(movieId: Int, isCollected: Boolean): Single<Boolean> {
        return if (isCollected) {
            tvShowDao.uncollectMovie(movieId = movieId)
        } else {
            val collectedDb = CollectedDb(movieId = movieId, true)
            tvShowDao.collectMovie(collectedDb = collectedDb)
        }
            .andThen(Single.just(!isCollected))
    }*/

}