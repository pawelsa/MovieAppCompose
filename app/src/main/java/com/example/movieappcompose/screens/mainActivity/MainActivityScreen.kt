package com.example.movieappcompose.screens.mainActivity

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.movieappcompose.R
import com.example.movieappcompose.screens.movieScreen.MovieScreen
import com.example.movieappcompose.screens.tv_shows.TvShowsPage
import com.example.movieappcompose.widgets.BottomNavigationBar
import com.example.movieappcompose.widgets.Center
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
                1 -> TvShowsPage(mainActivityViewModel, backDispatcher)
                2 -> Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
                    Center {
                        Text(
                            text = stringResource(R.string.profile_in_construction),
                            style = MaterialTheme.typography.h1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> MovieScreen(mainActivityViewModel, backDispatcher)
            }
        }
    }
}