package com.example.movieappcompose.screens.mainActivity

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.movieappcompose.screens.movieScreen.MovieScreen
import com.example.movieappcompose.screens.tv_shows.TvShowsPage
import com.example.movieappcompose.widgets.BottomNavigationBar
import com.example.movieappcompose.widgets.Page


@ExperimentalMaterialApi
@Composable
fun MainScreen(
    mainActivityViewModel: MainActivityViewModel,
    backDispatcher: OnBackPressedDispatcher,
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                show = mainActivityViewModel.showBottomNavigationBar,
                current = mainActivityViewModel.currentSelectedItemInNavigationBar,
                onSelect = { mainActivityViewModel.currentSelectedItemInNavigationBar = it }
            )
        }
    ) {
        Crossfade(targetState = mainActivityViewModel.currentSelectedItemInNavigationBar) { currentPage ->
            when (currentPage) {
                1 -> TvShowsPage(backDispatcher)
                2 -> Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) { }
                else -> MovieScreen(mainActivityViewModel, backDispatcher)
            }
        }
    }
}