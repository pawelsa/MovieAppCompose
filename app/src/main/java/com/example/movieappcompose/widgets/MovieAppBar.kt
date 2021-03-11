package com.example.movieappcompose.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.ui.ethnocentricFont


private val AppBarHeight = 56.dp
private val AppBarHorizontalPadding = 4.dp
private val TitleInsetWithoutIcon = Modifier.width(16.dp - AppBarHorizontalPadding)
private val TitleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - AppBarHorizontalPadding)

@Composable
fun MovieAppBar(
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Row(
        Modifier
                .fillMaxWidth()
                .padding(start = AppBarHorizontalPadding, end = AppBarHorizontalPadding)
                .height(AppBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            val emphasisLevels = LocalAbsoluteElevation.current
            if (navigationIcon == null) {
                Spacer(TitleInsetWithoutIcon)
            } else {
                Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
//                    Elevation(emphasisLevels.high, navigationIcon)
                    navigationIcon
                }
            }

            title?.let {
                Row(
                    Modifier
                            .fillMaxHeight()
                            .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.h6.copy(fontFamily = ethnocentricFont)) {
//                        ProvideEmphasis(emphasisLevels.high, it)
                        it
                    }
                }
            }

//            CompositionLocalProvider(emphasisLevels.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
//            }
        }
    )
}