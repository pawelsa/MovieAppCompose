package com.example.movieappcompose.utlis

import androidx.compose.runtime.compositionLocalOf
import com.example.movieappcompose.screens.movieScreen.Actions

val LocalActions = compositionLocalOf<Actions> { error("Couldn't find action") }