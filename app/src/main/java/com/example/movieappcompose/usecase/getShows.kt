package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.repositories.TvShowRepository
import io.reactivex.rxjava3.core.Observable

interface GetShowsUseCase : UseCase<GetShowsUseCase.Param, Observable<List<TvShow>>> {
    data class Param(val page: Int)
}

class GetPopularShowsUseCase(private val tvShowRepository: TvShowRepository) :
    GetShowsUseCase {
    override fun run(param: GetShowsUseCase.Param): Observable<List<TvShow>> =
        tvShowRepository.getPopularShows(param.page)
}

class GetTopRatedShowsUseCase(private val tvShowRepository: TvShowRepository) :
    GetShowsUseCase {
    override fun run(param: GetShowsUseCase.Param): Observable<List<TvShow>> =
        tvShowRepository.getTopRatedShows(param.page)
}
