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
import com.example.movieappcompose.base.OnSelected
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.MovieTabRow

@Composable
fun MovieHomeTabRow(
    pageSelected: Int,
    onPageSelected: OnSelected,
){
    val selectedTabTextStyle = MaterialTheme.typography.h2
    val notSelectedTabTextStyle =
        MaterialTheme.typography.h2.copy(color = MovieColors.nonSelectedText)
    val getTextStyle: (Int) -> TextStyle =
        { if (pageSelected == it) selectedTabTextStyle else notSelectedTabTextStyle }
    MovieTabRow(pageSelected = pageSelected) {
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
