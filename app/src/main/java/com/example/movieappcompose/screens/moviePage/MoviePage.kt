package com.example.movieappcompose.screens.moviePage

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
import com.example.movieappcompose.screens.moviePage.viewModel.MovieListViewModel
import com.example.movieappcompose.widgets.MovieAppBar
import com.example.movieappcompose.widgets.Page

@Composable
fun MoviePage() {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    val viewModel: MainScreenViewModel = viewModel()

    Page(mainActivityViewModel.showBottomNavigationBar) {
        MoviePage(
            onSearchPressed = mainActivityViewModel::changeBottomNavigationBarVisibility,
            pageSelected = viewModel.state.pageSelected,
            onPageSelected = viewModel::selectPage
        )
    }
}

@Composable
fun MoviePage(
    pageSelected: Int,
    onSearchPressed: () -> Unit,
    onPageSelected: (Int) -> Unit,
) {
    val viewModel: MovieListViewModel = viewModel()
    /*onActive {
        viewModel.getMovies()
    }*/

    Column {
        MovieAppBar(
            title = { Text(text = stringResource(id = R.string.main_title)) },
            actions = {
                IconButton(onClick = onSearchPressed) {
                    Icon(Icons.Outlined.Search, "")
                }
            })
        MainScreenTabBarPager(
            pageSelected = pageSelected,
            onPageSelected = onPageSelected,
            viewModel = viewModel
        )
    }
}

