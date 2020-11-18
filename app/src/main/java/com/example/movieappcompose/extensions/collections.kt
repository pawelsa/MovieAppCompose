package com.example.movieappcompose.extensions

fun <T> List<T>.skipLast(skip: Int) = this.take(this.size - skip)