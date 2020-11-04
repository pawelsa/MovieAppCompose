package com.example.movieappcompose.extensions

import io.reactivex.rxjava3.core.Single

fun <T> singleOf(content: T) = Single.just(content)