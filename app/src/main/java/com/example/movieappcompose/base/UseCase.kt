package com.example.movieappcompose.base

interface UseCase<IN, OUT> {
    fun run(param:IN): OUT
    operator fun invoke(param: IN): OUT = run(param)
}