package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.screens.moviePage.viewModel.MovieListState
import com.example.movieappcompose.screens.moviePage.viewModel.MovieListViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.LocalActions
import com.example.movieappcompose.widgets.Center
import com.example.movieappcompose.widgets.pager_temp.ExperimentalPagerApi
import com.example.movieappcompose.widgets.pager_temp.HorizontalPager
import com.example.movieappcompose.widgets.pager_temp.PagerState
import com.example.movieappcompose.widgets.pager_temp.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreenTabBarPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
    viewModel: MovieListViewModel,
) {
    val pagerState =
        rememberPagerState(
            pageCount = 2,
            pageChanged = onPageSelected,
            initialPage = pageSelected,
        )

    val scope = rememberCoroutineScope()

    MovieHomeTabRow(pagerState = pagerState, onPageSelected = {
        scope.launch {
            pagerState.animateScrollToPage(it)
        }
        onPageSelected(it)
    })
    Spacer(
        modifier = Modifier
                .requiredHeight(Dimen.margin.small)
                .fillMaxWidth()
    )
    MainScreenTabBarPager(
        pagerState = pagerState,
        state = viewModel.state,
        loadMoreData = {
            when (it) {
                0 -> viewModel.getPopular()
                1 -> viewModel.getUpcoming()
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreenTabBarPager(
    pagerState: PagerState,
    loadMoreData: (Int) -> Unit,
    state: MovieListState,
) {

    when (state) {
        is MovieListState.Init -> LoadingMoviesPager(
            pagerState = pagerState,
        )
        is MovieListState.Loaded -> MoviesPager(
            pagerState = pagerState,
            popularMovies = state.popularMovies,
            upcomingMovies = state.upcomingMovies,
            loadMoreData = loadMoreData
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoadingMoviesPager(
    pagerState: PagerState,
) {
    HorizontalPager(state = pagerState) {
        Center {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesPager(
    pagerState: PagerState,
    loadMoreData: (Int) -> Unit,
    popularMovies: List<Movie>,
    upcomingMovies: List<Movie>,
) {
    val selectMovie = LocalActions.current.selectMovie

    HorizontalPager(state = pagerState, offscreenLimit = 3) { page ->
        val movies = when (page) {
            1 -> popularMovies
            else -> upcomingMovies
        }
        MainScreenMovieList(movies, loadMoreData = {
            loadMoreData(page)
        }) { selectMovie(it) }
    }
}
