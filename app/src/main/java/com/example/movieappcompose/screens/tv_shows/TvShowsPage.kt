package com.example.movieappcompose.screens.tv_shows

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.moviePage.viewModel.MainScreenViewModel
import com.example.movieappcompose.screens.tv_shows.viewModel.TvShowListViewModel
import com.example.movieappcompose.widgets.MovieAppBar
import com.example.movieappcompose.widgets.Page

@Composable
fun TvShowsPage() {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val viewModel: MainScreenViewModel = viewModel()

    Page(mainActivityViewModel.showBottomNavigationBar) {
        TvShowsPage(
            onSearchPressed = mainActivityViewModel::changeBottomNavigationBarVisibility,
            pageSelected = viewModel.state.pageSelected,
            onPageSelected = viewModel::selectPage
        )
    }
}

@Composable
fun TvShowsPage(
    pageSelected: Int,
    onSearchPressed: () -> Unit,
    onPageSelected: (Int) -> Unit,
) {
    val viewModel: TvShowListViewModel = viewModel()

    Column {
        MovieAppBar(
            title = { Text(text = stringResource(id = R.string.tv_shows_title)) },
            actions = {
                IconButton(onClick = onSearchPressed) {
                    Icon(Icons.Outlined.Search, "")
                }
            })
        TvShowsTabBarPager(
            pageSelected = pageSelected,
            onPageSelected = onPageSelected,
            viewModel = viewModel
        )
    }
}

