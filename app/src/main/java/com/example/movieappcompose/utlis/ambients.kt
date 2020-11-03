package com.example.movieappcompose.utlis

import androidx.compose.runtime.ambientOf
import com.example.movieappcompose.screens.movieScreen.Actions

val ActionsAmbient = ambientOf<Actions> { error("Couldn't find action") }