package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.moviePage.viewModel.MainScreenViewModel
import com.example.movieappcompose.widgets.MovieAppBar
import com.example.movieappcompose.widgets.Page


@Composable
fun MoviePage() {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val mainScreenViewModel: MainScreenViewModel = viewModel()

    Page(mainActivityViewModel.showBottomNavigationBar) {
        MoviePage(
            onSearchPressed = mainActivityViewModel::changeBottomNavigationBarVisibility,
            pageSelected = mainScreenViewModel.currentPage,
            onPageSelected = mainScreenViewModel::currentPage
        )
    }

}


@Composable
fun MoviePage(
    pageSelected: Int,
    onSearchPressed: () -> Unit,
    onPageSelected: (Int) -> Unit,
) {
    Column {
        MovieAppBar(
            title = { Text(text = stringResource(id = R.string.main_title)) },
            actions = {
                IconButton(onClick = onSearchPressed) {
                    Icon(Icons.Outlined.Search)
                }
            })
        MainScreenTabBarPager(
            pageSelected = pageSelected,
            onPageSelected = onPageSelected,
        )
    }
}

