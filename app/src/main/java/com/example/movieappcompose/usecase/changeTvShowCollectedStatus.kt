package com.example.movieappcompose.usecase

import com.example.movieappcompose.data.repositories.TvShowRepository
import io.reactivex.rxjava3.core.Single

interface ChangeTvShowCollectedStatusUseCase {
    operator fun invoke(param: Param): Single<Boolean>

    data class Param(val tvShowId: Int, val isCollected: Boolean)
}

class ChangeTvShowCollectedStatusUseCaseImpl(private val tvShowRepository: TvShowRepository) :
    ChangeTvShowCollectedStatusUseCase {
    override fun invoke(param: ChangeTvShowCollectedStatusUseCase.Param): Single<Boolean> =
        tvShowRepository.changeTvShowCollectStatus(param.tvShowId, param.isCollected)
}