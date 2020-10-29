package com.example.movieappcompose.screens.moviePage

import androidx.compose.animation.animate
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.R
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun MovieTabRow(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
) {
    val selectedTabTextStyle = MaterialTheme.typography.h6
    val notSelectedTabTextStyle =
        MaterialTheme.typography.h6.copy(color = MovieColors.nonSelectedText)
    val getTextStyle: (Int) -> TextStyle =
        { if (pageSelected == it) selectedTabTextStyle else notSelectedTabTextStyle }

    // TODO: 28/10/2020 modify [ScrollableTabRow] to allow adding padding between tabs and indicator
    ScrollableTabRow(
        selectedTabIndex = pageSelected,
        backgroundColor = Color.Transparent,
        edgePadding = Dimen.tabBarBorderSpacer,
        indicator = {
            DefaultIndicator(it[pageSelected])
        },
        divider = { }
    ) {
        Tab(selected = pageSelected == 0, onClick = { onPageSelected(0) }) {
            Text(
                text = AnnotatedString(stringResource(id = R.string.main_tab_new_popular)),
                style = getTextStyle(0),
                modifier = Modifier.padding(Dimen.paddingSmall)
            )
        }
        Tab(selected = pageSelected == 1, onClick = { onPageSelected(1) }) {
            Text(
                text = AnnotatedString(stringResource(id = R.string.main_tab_the_upcoming)),
                style = getTextStyle(1),
                modifier = Modifier.padding(Dimen.paddingSmall)
            )
        }
    }
}

@Composable
fun DefaultIndicator(currentTabPosition: TabPosition) {
    val modifier = Modifier.defaultTabIndicatorOffset(currentTabPosition)
    Row(modifier = modifier) {
        Box(
            Modifier
                .padding(end = 5.dp)
                .width(20.dp)
                .preferredHeight(5.dp)
                .clip(RoundedCornerShape(2.5.dp))
                .background(color = MovieColors.yellow)
        )
        Box(
            Modifier
                .width(5.dp)
                .preferredHeight(5.dp)
                .clip(RoundedCornerShape(2.5.dp))
                .background(color = MovieColors.yellow)
        )
    }
}

fun Modifier.defaultTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed {
    // TODO: should we animate the width of the indicator as it moves between tabs of different
    // sizes inside a scrollable tab row?
    val currentTabWidth = currentTabPosition.width
    val middleOfSelectedTab = currentTabPosition.left + currentTabWidth / 2 - 15.dp
    val indicatorOffset = animate(
        target = middleOfSelectedTab,
        animSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .preferredWidth(currentTabWidth)
}