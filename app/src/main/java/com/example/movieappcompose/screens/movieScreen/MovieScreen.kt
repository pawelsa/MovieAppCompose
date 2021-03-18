package com.example.movieappcompose.screens.movieScreen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.movieappcompose.screens.actors.ActorsPage
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.movieDetail.MovieDetailPage
import com.example.movieappcompose.screens.moviePage.MoviePage
import com.example.movieappcompose.ui.rememberNavigator
import com.example.movieappcompose.utlis.LocalActions

@ExperimentalMaterialApi
@Composable
fun MovieScreen(
    mainActivityViewModel: MainActivityViewModel,
    backDispatcher: OnBackPressedDispatcher,
) {
    val navigator = rememberNavigator(backDispatcher, Destination.Home)
    val actions = remember(navigator) { Actions(navigator) }

    CompositionLocalProvider(LocalActions provides actions) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.Home -> {
                    mainActivityViewModel.showBottomNavigationBar = true
                    MoviePage()
                }
                is Destination.MovieDetail -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    MovieDetailPage(
                        movie = destination.movie
                    )
                }
                is Destination.SelectingMovieSeat -> Box { }
                is Destination.MovieTicket -> Box { }
                is Destination.ActorsList -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    ActorsPage(destination.movie)
                }
            }
        }
    }

}
