package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.data.repositories.MovieRepository
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

interface GetDetailedMoviesUseCase :
    UseCase<GetDetailedMoviesUseCase.Params, Observable<DetailedMovie>> {
    data class Params(val movie: Movie)
}

class GetDetailedMoviesUseCaseImpl(private val _movieRepository: MovieRepository) :
    GetDetailedMoviesUseCase {
    override fun run(param: GetDetailedMoviesUseCase.Params): Observable<DetailedMovie> {
        var initialDetailedMovie = DetailedMovie(param.movie)
        val disposables = CompositeDisposable()

        return PublishSubject.create<DetailedMovie>()
            .apply<PublishSubject<DetailedMovie>> {
                doOnSubscribe {
                    disposables +=
                        _movieRepository.getMovieReviews(param.movie.id)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                initialDetailedMovie = initialDetailedMovie.copy(reviews = it)
                                onNext(initialDetailedMovie)
                            }, {})

                    disposables +=
                        _movieRepository.getMovieDiscussion(param.movie.id)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                initialDetailedMovie = initialDetailedMovie.copy(discussion = it)
                                onNext(initialDetailedMovie)
                            }, {})

                    disposables +=
                        _movieRepository.isMovieCollected(param.movie.id)
                            .subscribeOn(Schedulers.io())
                            .subscribe({
                                initialDetailedMovie = initialDetailedMovie.copy(isCollected = it)
                                onNext(initialDetailedMovie)
                            }, {})
                }
            }
            .doOnDispose {
                disposables.dispose()
            }
    }
}