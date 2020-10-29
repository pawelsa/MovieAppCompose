package com.example.movieappcompose.usecase

import com.example.movieappcompose.base.UseCase

interface GetMoviesUseCase: UseCase<Unit, List<String>>

class GetPopularMoviesUseCase:GetMoviesUseCase{
    override fun run(param: Unit): List<String> = listOf("Fight club", "Django", "Jaws", "Star wars")
}

class GetUpcomingMoviesUseCase:GetMoviesUseCase{
    override fun run(param: Unit): List<String> = listOf("James Bond: No time to die" ,"Sherlock Holmes 3", "Avatar 2", "Black Widow")
}