package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

interface GetDetailedMoviesUseCase :
    UseCase<GetDetailedMoviesUseCase.Params, Observable<DetailedMovie>> {
    data class Params(val movie: Movie)
}

class GetDetailedMoviesUseCaseImpl(private val _movieRepository: MovieRepository) :
    GetDetailedMoviesUseCase {
    override fun run(param: GetDetailedMoviesUseCase.Params): Observable<DetailedMovie> {
        var initialDetailedMovie = DetailedMovie(param.movie)
        val disposables = CompositeDisposable()


        val reviewsSource = _movieRepository.getMovieReviews(param.movie.id).startWith(
            Single.just(
                emptyList()
            )
        ).map {
            initialDetailedMovie = initialDetailedMovie.copy(reviews = it)
            initialDetailedMovie
        }.toObservable()
        val discussionsSource = _movieRepository.getMovieDiscussion(param.movie.id).startWith(
            Single.just(
                emptyList()
            )
        ).map {
            initialDetailedMovie = initialDetailedMovie.copy(discussion = it)
            initialDetailedMovie
        }.toObservable()
        val isMovieCollectedSource = _movieRepository.isMovieCollected(param.movie.id).map {
            initialDetailedMovie = initialDetailedMovie.copy(isCollected = it)
            initialDetailedMovie
        }.toObservable()


        return Observable.combineLatest(
            listOf(
                isMovieCollectedSource,
                discussionsSource,
                reviewsSource,
            )
        ) { (reviewsSource, discussionSource, collectedSource) ->
            DetailedMovie(
                movie = param.movie,
                reviews = (reviewsSource as DetailedMovie).reviews,
                discussion = (discussionSource as DetailedMovie).discussion,
                isCollected = (collectedSource as DetailedMovie).isCollected,
            )
        }
    }
}