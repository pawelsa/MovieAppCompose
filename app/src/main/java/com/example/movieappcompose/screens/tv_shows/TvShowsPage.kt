package com.example.movieappcompose.screens.tv_shows

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.screens.actors.ActorsPage
import com.example.movieappcompose.screens.actors.ActorsPageDetails
import com.example.movieappcompose.screens.actors.Person
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.moviePage.viewModel.MainScreenViewModel
import com.example.movieappcompose.screens.showDetail.ShowDetailPage
import com.example.movieappcompose.screens.tv_shows.viewModel.TvShowListViewModel
import com.example.movieappcompose.ui.rememberNavigator
import com.example.movieappcompose.utlis.LocalShowActions
import com.example.movieappcompose.widgets.MovieAppBar
import com.example.movieappcompose.widgets.Page

@Composable
fun TvShowsPage(
    mainActivityViewModel: MainActivityViewModel,
    backDispatcher: OnBackPressedDispatcher,
) {
    val viewModel: MainScreenViewModel = viewModel()

    val navigator = rememberNavigator(backDispatcher, ShowDestination.Home)
    val actions = remember(navigator) { ShowActions(navigator) }
    CompositionLocalProvider(LocalShowActions provides actions) {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                is ShowDestination.Home -> {
                    mainActivityViewModel.showBottomNavigationBar = true
                    TvShowsPage(
                        onSearchPressed = mainActivityViewModel::changeBottomNavigationBarVisibility,
                        pageSelected = viewModel.state.pageSelected,
                        onPageSelected = viewModel::selectPage
                    )
                }
                is ShowDestination.ShowDetail -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    ShowDetailPage(tvShow = destination.show)
                }
                is ShowDestination.ActorsList -> {
                    mainActivityViewModel.showBottomNavigationBar = false
                    ActorsPage(
                        upPress = LocalShowActions.current.upPress,
                        actorsPageDetails = ActorsPageDetails(
                            title = destination.show.title,
                            castList = destination.show.cast.map { it ->
                                Person(
                                    name = it.name,
                                    position = it.character,
                                    profilePicture = it.profilePath
                                )
                            },
                            crewList = destination.show.crew.map {
                                Person(
                                    name = it.name,
                                    position = it.job,
                                    profilePicture = it.profilePath
                                )
                            }
                        )
                    )
                }
            }

        }
    }
}

@Composable
fun TvShowsPage(
    pageSelected: Int,
    onSearchPressed: () -> Unit,
    onPageSelected: (Int) -> Unit,
) {
    val viewModel: TvShowListViewModel = viewModel()
    val mainActivityViewModel: MainActivityViewModel = viewModel()

    Page(mainActivityViewModel.showBottomNavigationBar) {
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
}

