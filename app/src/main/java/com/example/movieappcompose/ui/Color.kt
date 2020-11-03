package com.example.movieappcompose.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.VerticalGradient

object MovieColors {
    val greyPill = Color(0xFFACADAD)
    val greyButtonText = Color(0xFF9B9B9B)
    val yellow = Color(0xFFFFBC06)
    val nonSelectedText = Color(0xFFA9AAAA)
    val greyText = Color(0xFFACADAD)
    val backgroundStart = Color.White
    val backgroundEnd = Color(0xFFE7E7E7)
    val greyButton = Color(0xFFEAEAEA)
    val pageGradient = { height: Float ->
        VerticalGradient(
            0.0f to backgroundStart,
            1.0f to backgroundEnd,
            startY = 0f,
            endY = height
        )
    }

}