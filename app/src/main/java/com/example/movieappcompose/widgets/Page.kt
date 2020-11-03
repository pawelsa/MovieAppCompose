package com.example.movieappcompose.widgets

import androidx.compose.animation.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun Page(showBottomBar: Boolean, content: @Composable () -> Unit) {
    WithConstraints {
        Box(
            modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(
                        MovieColors.pageGradient(constraints.maxHeight.toFloat())
                    )
        ) {
            val columnHeight = if (showBottomBar) maxHeight - Dimen.bottomBarHeight else maxHeight
            Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .height(animate(target = columnHeight))
            ) {
                content()
            }
        }
    }
}