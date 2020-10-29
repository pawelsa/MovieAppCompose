package com.example.movieappcompose.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Center(content: @Composable ()->Unit){
    Box(modifier = Modifier.fillMaxSize(), alignment = Alignment.Center) {
        content()
    }
}