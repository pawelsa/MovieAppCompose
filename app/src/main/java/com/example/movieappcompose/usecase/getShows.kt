package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.data.repositories.PersistPopularShowsList
import com.example.movieappcompose.data.repositories.PersistTopRatedShowsList
import com.example.movieappcompose.data.repositories.PersistTvShowLists
import io.reactivex.rxjava3.core.Observable

interface GetShowsUseCase : UseCase<GetShowsUseCase.Param, Observable<List<TvShow>>> {
    data class Param(val page: Int)
}

class GetPopularShowsUseCase(private val tvShowRepository: PersistPopularShowsList) :
    GetShowsUseCase {
    override fun run(param: GetShowsUseCase.Param): Observable<List<TvShow>> =
        tvShowRepository.get(PersistTvShowLists.Param(param.page))
}

class GetTopRatedShowsUseCase(private val tvShowRepository: PersistTopRatedShowsList) :
    GetShowsUseCase {
    override fun run(param: GetShowsUseCase.Param): Observable<List<TvShow>> =
        tvShowRepository.get(PersistTvShowLists.Param(param.page))
}
