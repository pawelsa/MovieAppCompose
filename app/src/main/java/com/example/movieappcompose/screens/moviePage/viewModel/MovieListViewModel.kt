package com.example.movieappcompose.screens.moviePage.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.usecase.GetMoviesUseCase
import com.example.movieappcompose.usecase.GetPopularMoviesUseCase
import com.example.movieappcompose.usecase.GetUpcomingMoviesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.zipWith
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieListViewModel @ViewModelInject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) :
    BaseViewModel<MovieListState>(MovieListState.Init) {
    private var popularMovies = emptyList<Movie>()
    private var upcomingMovies = emptyList<Movie>()

    fun getMovies() {
        val pageParam = GetMoviesUseCase.Param(1)
        disposables += getUpcomingMoviesUseCase(pageParam)
                .zipWith(
                    getPopularMoviesUseCase(
                        pageParam
                    )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { moviesPair: Pair<List<Movie>, List<Movie>> ->
                    upcomingMovies = moviesPair.first
                    popularMovies = moviesPair.second
                    state = MovieListState.Loaded(
                        popularMovies = popularMovies,
                        upcomingMovies = upcomingMovies
                    )
                }
    }
/*
    TODO might be used when adding refreshing
    private fun getUpcoming() {
        disposables += getUpcomingMoviesUseCase(GetMoviesUseCase.Param(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    state = MovieListState.Loaded(
                        popularMovies = popularMovies,
                        upcomingMovies = upcomingMovies
                    )
                }
    }

    private fun getPopular() {
        disposables += getPopularMoviesUseCase(GetMoviesUseCase.Param(1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    popularMovies = it
                    state = MovieListState.Loaded(
                        popularMovies = popularMovies,
                        upcomingMovies = upcomingMovies
                    )
                }
    }*/
}