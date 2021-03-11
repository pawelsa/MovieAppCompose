package com.example.movieappcompose.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun Page(showBottomBar: Boolean, content: @Composable () -> Unit) {
    BoxWithConstraints {
        Box(
            modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(
                        brush = MovieColors.pageGradient
                    )
        ) {
            val columnHeight =
                if (showBottomBar) this@BoxWithConstraints.maxHeight - Dimen.bottomBarHeight else this@BoxWithConstraints.maxHeight
            Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .height(columnHeight)
            ) {
                content()
            }
        }
    }
}