package com.example.movieappcompose.widgets

import androidx.compose.animation.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.VerticalGradient
import com.example.movieappcompose.ui.Dimen

@Composable
fun Page(showBottomBar: Boolean, content: @Composable () -> Unit) {
    WithConstraints {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    VerticalGradient(
                        0.0f to Color.White,
                        1.0f to Color(0xFFE7E7E7),
                        startY = 0f,
                        endY = constraints.maxHeight.toFloat()
                    )
                )
        ){
            val columnHeight = if (showBottomBar) maxHeight - Dimen.bottomBarHeight else maxHeight
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(animate(target = columnHeight))
            ) {
                content()
            }
        }
    }
}