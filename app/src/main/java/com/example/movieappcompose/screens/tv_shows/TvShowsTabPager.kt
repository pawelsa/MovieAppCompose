package com.example.movieappcompose.screens.tv_shows

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.screens.tv_shows.viewModel.TvShowListState
import com.example.movieappcompose.screens.tv_shows.viewModel.TvShowListViewModel
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
fun TvShowsTabBarPager(
    pageSelected: Int,
    onPageSelected: (Int) -> Unit,
    viewModel: TvShowListViewModel,
) {
    val pagerState =
        rememberPagerState(
            pageCount = 2,
            pageChanged = onPageSelected,
            initialPage = pageSelected,
        )

    val scope = rememberCoroutineScope()

    TvShowHomeTabRow(pagerState = pagerState, onPageSelected = {
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
    TvShowsTabBarPager(
        pagerState = pagerState,
        state = viewModel.state,
        loadMoreData = {
            when (it) {
                0 -> viewModel.getPopular()
                1 -> viewModel.getTopRated()
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TvShowsTabBarPager(
    pagerState: PagerState,
    loadMoreData: (Int) -> Unit,
    state: TvShowListState,
) {

    when (state) {
        is TvShowListState.Init -> LoadingMoviesPager(
            pagerState = pagerState,
        )
        is TvShowListState.Loaded -> MoviesPager(
            pagerState = pagerState,
            popularMovies = state.popularShows,
            upcomingMovies = state.topShows,
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
    popularMovies: List<TvShow>,
    upcomingMovies: List<TvShow>,
) {
    val selectMovie = LocalActions.current.selectShow

    HorizontalPager(state = pagerState, offscreenLimit = 3) { page ->
        val movies = when (page) {
            1 -> popularMovies
            else -> upcomingMovies
        }
        TvShowsList(movies, loadMoreData = {
            loadMoreData(page)
        }) { selectMovie(it) }
    }
}
