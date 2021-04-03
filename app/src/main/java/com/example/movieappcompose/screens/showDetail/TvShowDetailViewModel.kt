package com.example.movieappcompose.screens.showDetail

import com.example.movieappcompose.base.BaseViewModel
import com.example.movieappcompose.data.models.tv_shows.DetailedShow
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.usecase.ChangeMovieCollectedStatusUseCase
import com.example.movieappcompose.usecase.GetDetailedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getDetailedMoviesUseCase: GetDetailedTvShowUseCase,
    private val changeMovieCollectedStatusUseCase: ChangeMovieCollectedStatusUseCase
) : BaseViewModel<TvShowDetailState>(
    TvShowDetailState.Init
) {
    private lateinit var detailedShow: DetailedShow

    fun setShow(show: TvShow) {
        this.detailedShow =
            DetailedShow(tvShow = show, reviews = emptyList(), discussion = emptyList())
        state = TvShowDetailState.LoadedTvShow(this.detailedShow)
        // TODO: 03/11/2020 get movie details and set new state
        disposables += getDetailedMoviesUseCase(GetDetailedTvShowUseCase.Params(show))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ detailedMovie ->
                this.detailedShow = detailedMovie
                state = TvShowDetailState.LoadedTvShowDetails(this.detailedShow)
            }, {
                Timber.e(it)
            })
    }

/*    fun changeCollectedStatus(tvShow: TvShow) {
        disposables += changeMovieCollectedStatusUseCase(
            ChangeMovieCollectedStatusUseCase.Param(
                tvShow.id,
                this.detailedShow.isCollected
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.detailedShow = this.detailedShow.copy(isCollected = it)
                state = TvShowDetailState.LoadedTvShowDetails(this.detailedShow)
            }, {
                Timber.e(it)
            })
    }*/
}