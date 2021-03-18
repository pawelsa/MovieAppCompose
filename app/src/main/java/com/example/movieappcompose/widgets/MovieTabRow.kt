package com.example.movieappcompose.widgets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.base.OnSelected
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.ui.notSelectedTabTextStyle
import com.example.movieappcompose.ui.selectedTabTextStyle


@Composable
fun <T> MovieTabRow(
    tabs: List<T>,
    pageSelected: Int,
    onPageSelected: OnSelected,
    modifier: Modifier = Modifier,
    content: @Composable (page: T) -> Unit,
) {

    // TODO: 28/10/2020 modify [ScrollableTabRow] to allow adding padding between tabs and indicator
    // TODO: 02/11/2020 add configuration for text style for both selected and un-selected tab
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pageSelected,
        backgroundColor = Color.Transparent,
        edgePadding = Dimen.tabBarBorderSpacer,
        indicator = {
            DefaultIndicator(it[pageSelected])
        },
        divider = { },
        tabs = {
            for (page in tabs.indices) {
                val isSelected = page == pageSelected
                ProvideTextStyle(value = if (isSelected) selectedTabTextStyle else notSelectedTabTextStyle) {
                    Tab(selected = isSelected, onClick = { onPageSelected(page) }) {
                        content(tabs[page])
                    }
                }

            }
        },
    )
}

@Composable
fun DefaultIndicator(currentTabPosition: TabPosition) {
    val modifier = Modifier.defaultTabIndicatorOffset(currentTabPosition)
    Row(modifier = modifier) {
        Box(
            Modifier
                    .padding(end = 5.dp)
                    .requiredWidth(20.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(2.5.dp))
                    .background(color = MovieColors.yellow)
        )
        Box(
            Modifier
                    .requiredWidth(5.dp)
                    .height(5.dp)
                    .clip(RoundedCornerShape(2.5.dp))
                    .background(color = MovieColors.yellow)
        )
    }
}

fun Modifier.defaultTabIndicatorOffset(
    currentTabPosition: TabPosition,
): Modifier = composed {
    // TODO: should we animate the width of the indicator as it moves between tabs of different
    //  sizes inside a scrollable tab row?
    val currentTabWidth = currentTabPosition.width
    val middleOfSelectedTab = currentTabPosition.left + currentTabWidth / 2 - 15.dp
    val indicatorOffset by animateDpAsState(
        targetValue = middleOfSelectedTab,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth)
}