package com.example.movieappcompose.widgets

import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.ui.ethnocentricFont


private val AppBarHeight = 56.dp
private val AppBarHorizontalPadding = 4.dp
private val TitleInsetWithoutIcon = Modifier.preferredWidth(16.dp - AppBarHorizontalPadding)
private val TitleIconModifier = Modifier.fillMaxHeight()
    .preferredWidth(72.dp - AppBarHorizontalPadding)

@Composable
fun MovieAppBar(
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Row(
        Modifier.fillMaxWidth()
            .padding(start = AppBarHorizontalPadding, end = AppBarHorizontalPadding)
            .preferredHeight(AppBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        children = {
            val emphasisLevels = AmbientEmphasisLevels.current
            if (navigationIcon == null) {
                Spacer(TitleInsetWithoutIcon)
            } else {
                Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                    ProvideEmphasis(emphasisLevels.high, navigationIcon)
                }
            }

            title?.let {
                Row(
                    Modifier.fillMaxHeight().weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.h6.copy(fontFamily = ethnocentricFont)) {
                        ProvideEmphasis(emphasisLevels.high, it)
                    }
                }
            }

            ProvideEmphasis(emphasisLevels.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    children = actions
                )
            }
        }
    )
}