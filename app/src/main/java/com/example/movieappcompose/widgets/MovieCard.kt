package com.example.movieappcompose.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieappcompose.ui.Dimen

@Composable
fun MovieCard(){
    Card {
        Text(text = "SomeText", modifier = Modifier.padding(Dimen.paddingMedium))
    }
}