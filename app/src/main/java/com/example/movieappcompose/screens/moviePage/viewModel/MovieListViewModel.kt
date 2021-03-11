package com.example.movieappcompose.screens.moviePage.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.usecase.GetMoviesUseCase
import com.example.movieappcompose.usecase.GetPopularMoviesUseCase
import com.example.movieappcompose.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : BaseViewModel<MovieListState>(MovieListState.Init) {
    private var popularMovies = emptyList<Movie>()
    private var upcomingMovies = emptyList<Movie>()
    private var popularMoviesPage by mutableStateOf(1)
    private var upcomingMoviesPage by mutableStateOf(1)

    companion object {
        private const val PAGE_SIZE = 20
    }

    fun getMovies() {
        getPopular()
        getUpcoming()
    }

    fun getUpcoming() {
        disposables += getUpcomingMoviesUseCase(GetMoviesUseCase.Param(upcomingMoviesPage++))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    upcomingMovies = it
                    upcomingMoviesPage = upcomingMovies.size / PAGE_SIZE + 1
                    state = MovieListState.Loaded(
                        popularMovies = popularMovies,
                        upcomingMovies = upcomingMovies
                    )
                    Timber.d("Result - up: ${upcomingMovies.size}, po: ${popularMovies.size}")
                }, {
                    Timber.e(it)
                }, {
                    Timber.d("Completed upcoming")
                })
    }

    fun getPopular() {
        disposables += getPopularMoviesUseCase(GetMoviesUseCase.Param(popularMoviesPage++))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    popularMovies = it
                    popularMoviesPage = popularMovies.size / PAGE_SIZE + 1
                    state = MovieListState.Loaded(
                        popularMovies = popularMovies,
                        upcomingMovies = upcomingMovies
                    )
                    Timber.d("Result - up: ${upcomingMovies.size}, po: ${popularMovies.size}")
                }, {
                    Timber.e(it)
                }, {
                    Timber.d("Completed popular")
                })
    }
}