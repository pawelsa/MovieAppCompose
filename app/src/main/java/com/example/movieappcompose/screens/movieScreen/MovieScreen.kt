package com.example.movieappcompose.screens.movieScreen

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.movieDetail.MovieDetailPage
import com.example.movieappcompose.screens.moviePage.MoviePage
import com.example.movieappcompose.ui.Navigator
import com.example.movieappcompose.utlis.ActionsAmbient

@ExperimentalMaterialApi
@Composable
fun MovieScreen(mainActivityViewModel: MainActivityViewModel, backDispatcher: OnBackPressedDispatcher) {
    val navigator: Navigator<Destination> =
        rememberSavedInstanceState(saver = Navigator.saver(backDispatcher)) {
            Navigator(Destination.Home, backDispatcher)
        }
    val actions = remember(navigator) { Actions(navigator) }

    Providers(ActionsAmbient provides actions) {
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
            }
        }
    }

}