package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.screens.moviePage.viewModel.PopularMoviesViewModel
import com.example.movieappcompose.screens.moviePage.viewModel.UpcomingMoviesViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.widgets.ViewPager


@Composable
fun MainScreenTabBarPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
) {
    MovieHomeTabRow(pageSelected = pageSelected, onPageSelected = onPageSelected)
    Spacer(
        modifier = Modifier
                .height(Dimen.margin.small)
                .fillMaxWidth()
    )
    ViewPager(
        selectedPage = pageSelected,
        onPageChanged = onPageSelected,
        items = listOf(
            viewModel<PopularMoviesViewModel>(),
            viewModel<UpcomingMoviesViewModel>()
        )
    ) { viewModel, _ ->
        MainScreenMovieList(viewModel = viewModel)
    }
}