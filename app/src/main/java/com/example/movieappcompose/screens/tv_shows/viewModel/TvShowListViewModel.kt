package com.example.movieappcompose.screens.tv_shows.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.usecase.GetPopularShowsUseCase
import com.example.movieappcompose.usecase.GetShowsUseCase
import com.example.movieappcompose.usecase.GetTopRatedShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowListViewModel @Inject constructor(
    private val getPopularShowsUseCase: GetPopularShowsUseCase,
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase,
) : BaseViewModel<TvShowListState>(TvShowListState.Init) {
    private var popularShows = emptyList<TvShow>()
    private var topRatedShows = emptyList<TvShow>()
    private var popularShowsPage by mutableStateOf(1)
    private var topRatedShowsPage by mutableStateOf(1)

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        getShows()
    }

    private fun getShows() {
        getPopular()
        getTopRated()
    }

    fun getTopRated() {
        disposables += getTopRatedShowsUseCase(GetShowsUseCase.Param(topRatedShowsPage++))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    topRatedShows = it
                    topRatedShowsPage = topRatedShows.size / PAGE_SIZE + 1
                    state = TvShowListState.Loaded(
                        popularShows = popularShows,
                        topShows = topRatedShows
                    )
                    Timber.d("Result - up: ${topRatedShows.size}, po: ${popularShows.size}")
                }, {
                Timber.e(it)
            })
    }

    fun getPopular() {
        disposables += getPopularShowsUseCase(GetShowsUseCase.Param(popularShowsPage++))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    popularShows = it
                    popularShowsPage = popularShows.size / PAGE_SIZE + 1
                    state = TvShowListState.Loaded(
                        popularShows = popularShows,
                        topShows = topRatedShows
                    )
                    Timber.d("Result - up: ${topRatedShows.size}, po: ${popularShows.size}")
                }, {
                Timber.e(it)
            })
    }
}