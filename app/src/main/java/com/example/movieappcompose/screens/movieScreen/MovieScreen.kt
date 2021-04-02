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
import com.example.movieappcompose.utlis.LocalMovieActions

@ExperimentalMaterialApi
@Composable
fun MovieScreen(
    mainActivityViewModel: MainActivityViewModel,
    backDispatcher: OnBackPressedDispatcher,
) {
    val navigator = rememberNavigator(backDispatcher, MovieDestination.Home)
    val actions = remember(navigator) { MovieActions(navigator) }

    CompositionLocalProvider(LocalMovieActions provides actions) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                MovieDestination.Home -> {
                    mainActivityViewModel.showBottomNavigationBar = true
                    MoviePage()
                }
                is MovieDestination.MovieDetail -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    MovieDetailPage(
                        movie = destination.movie
                    )
                }
                is MovieDestination.SelectingMovieSeat -> Box { }
                is MovieDestination.MovieTicket -> Box { }
                is MovieDestination.ActorsList -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    ActorsPage(destination.movie)
                }
            }
        }
    }

}
