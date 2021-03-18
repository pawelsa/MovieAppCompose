package com.example.movieappcompose.screens.moviePage

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnSelected
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.widgets.MovieTabRow
import com.example.movieappcompose.widgets.pager_temp.ExperimentalPagerApi
import com.example.movieappcompose.widgets.pager_temp.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieHomeTabRow(
    pagerState: PagerState,
    onPageSelected: OnSelected,
) {
    val tabs = listOf<@StringRes Int>(R.string.main_tab_new_popular, R.string.main_tab_the_upcoming)

    MovieTabRow(
        pageSelected = pagerState.currentPage,
        onPageSelected = onPageSelected,
        tabs = tabs,
    ) { textId ->
        Text(
            text = stringResource(id = textId),
            modifier = Modifier.padding(Dimen.padding.small)
        )
    }
}
