package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.screens.moviePage.viewModel.MovieListState
import com.example.movieappcompose.screens.moviePage.viewModel.MovieListViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.ActionsAmbient
import com.example.movieappcompose.widgets.Center
import com.example.movieappcompose.widgets.ViewPager


@Composable
fun MainScreenTabBarPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
    viewModel: MovieListViewModel
) {
    MovieHomeTabRow(pageSelected = pageSelected, onPageSelected = onPageSelected)
    Spacer(
        modifier = Modifier
                .height(Dimen.margin.small)
                .fillMaxWidth()
    )
    MainScreenTabBarPager(
        pageSelected = pageSelected,
        onPageSelected = onPageSelected,
        state = viewModel.state,
        loadMoreData = {
            when (it) {
                0 -> viewModel.getPopular()
                1 -> viewModel.getUpcoming()
            }
        }
    )
}

@Composable
fun MainScreenTabBarPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
    loadMoreData: (Int) -> Unit,
    state: MovieListState
) {
    when (state) {
        is MovieListState.Init -> LoadingMoviesPager(
            pageSelected = pageSelected,
            onPageSelected = onPageSelected,
        )
        is MovieListState.Loaded -> MoviesPager(
            pageSelected = pageSelected,
            onPageSelected = onPageSelected,
            popularMovies = state.popularMovies,
            upcomingMovies = state.upcomingMovies,
            loadMoreData = loadMoreData
        )
    }
}

@Composable
fun LoadingMoviesPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
) {
    ViewPager(
        noItems = 2,
        selectedPage = pageSelected,
        onPageChanged = onPageSelected,
    ) { _, _ ->
        Center {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MoviesPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
    loadMoreData: (Int) -> Unit,
    popularMovies: List<Movie>,
    upcomingMovies: List<Movie>,
) {
    val selectMovie = ActionsAmbient.current.selectMovie
    ViewPager(
        selectedPage = pageSelected,
        onPageChanged = onPageSelected,
        items = listOf(popularMovies, upcomingMovies)
    ) { movies, _ ->
        MainScreenMovieList(movies, loadMoreData = {
            loadMoreData(pageSelected)
        }) { selectMovie(it) }
    }
}
