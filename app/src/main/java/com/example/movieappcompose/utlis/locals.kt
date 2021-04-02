package com.example.movieappcompose.utlis

import androidx.compose.runtime.compositionLocalOf
import com.example.movieappcompose.screens.movieScreen.MovieActions
import com.example.movieappcompose.screens.tv_shows.ShowActions

val LocalMovieActions = compositionLocalOf<MovieActions> { error("Couldn't find action") }
val LocalShowActions = compositionLocalOf<ShowActions> { error("Couldn't find action") }