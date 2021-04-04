package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.tv_shows.DetailedShow
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.repositories.TvShowRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface GetDetailedTvShowUseCase :
    UseCase<GetDetailedTvShowUseCase.Params, Observable<DetailedShow>> {
    data class Params(val tvShow: TvShow)
}

class GetDetailedTvShowUseCaseImpl(private val tvShowRepository: TvShowRepository) :
    GetDetailedTvShowUseCase {
    override fun run(param: GetDetailedTvShowUseCase.Params): Observable<DetailedShow> {
        var initialDetailedTvShow = DetailedShow(param.tvShow)

        val reviewsSource = tvShowRepository.getTvShowReviews(param.tvShow.id).startWith(
            Single.just(
                emptyList()
            )
        ).map {
            initialDetailedTvShow = initialDetailedTvShow.copy(reviews = it)
            initialDetailedTvShow
        }.toObservable()
        val discussionsSource = tvShowRepository.getTvShowDiscussion(param.tvShow.id).startWith(
            Single.just(
                emptyList()
            )
        ).map {
            initialDetailedTvShow = initialDetailedTvShow.copy(discussion = it)
            initialDetailedTvShow
        }.toObservable()
        val isMovieCollectedSource = tvShowRepository.isShowCollected(param.tvShow.id).map {
            initialDetailedTvShow = initialDetailedTvShow.copy(isCollected = it)
            initialDetailedTvShow
        }.toObservable()


        return Observable.combineLatest(
            listOf(
                isMovieCollectedSource,
                discussionsSource,
                reviewsSource,
            )
        ) { (reviews, discussion, collected) ->
            DetailedShow(
                tvShow = param.tvShow,
                reviews = (reviews as DetailedShow).reviews,
                discussion = (discussion as DetailedShow).discussion,
                isCollected = (collected as DetailedShow).isCollected,
            )
        }
    }
}