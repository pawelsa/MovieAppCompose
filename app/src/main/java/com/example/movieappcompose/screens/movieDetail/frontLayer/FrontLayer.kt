package com.example.movieappcompose.screens.movieDetail.frontLayer

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnSelected
import com.example.movieappcompose.screens.movieDetail.MovieDetailState
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Center
import com.example.movieappcompose.widgets.MovieTabRow
import com.example.movieappcompose.widgets.ViewPager

@Composable
fun FrontLayer(
    movieDetailState: MovieDetailState,
    frontLayerViewModel: FrontLayerViewModel = viewModel(),
) {
    FrontLayer(
        pageSelected = frontLayerViewModel.state.page,
        onPageSelected = frontLayerViewModel::selectPage,
        movieDetailState
    )
}

@Composable
fun FrontLayer(
    pageSelected: Int,
    onPageSelected: OnSelected,
    movieDetailState: MovieDetailState
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                    .padding(vertical = Dimen.padding.big)
                    .height(Dimen.pillHeight)
                    .width(Dimen.pillWidth)
                    .clip(RoundedCornerShape(Dimen.corner.pill))
                    .background(MovieColors.greyPill)
                    .align(Alignment.CenterHorizontally),
        ) {}
        MovieTabRow(pageSelected = pageSelected) {
            Tab(
                selected = pageSelected == 0,
                onClick = { onPageSelected(0) },
                modifier = Modifier.padding(Dimen.padding.small)
            ) {
                Text(text = stringResource(id = R.string.detail_comments))
            }
            Tab(
                selected = pageSelected == 1,
                onClick = { onPageSelected(1) },
                modifier = Modifier.padding(Dimen.padding.small)
            ) {
                Text(text = stringResource(id = R.string.detail_discuss))
            }
        }
        Spacer(modifier = Modifier.height(Dimen.margin.big))
        when (movieDetailState) {
            is MovieDetailState.LoadedMovieDetails -> DataLoadedViewPager(
                pageSelected = pageSelected,
                onPageSelected = onPageSelected,
                // TODO: 03/11/2020 include list from model
                comments = List(30) { "This is my comment nr $it" },
                // TODO: 03/11/2020 include list from model
                discussionMessages = List(30) { "This is my discussion message nr $it" }
            )
            else -> LoadingDatingViewPage(
                pageSelected = pageSelected,
                onPageSelected = onPageSelected
            )
        }
    }
}

@Composable
fun DataLoadedViewPager(
    pageSelected: Int,
    onPageSelected: OnSelected,
    comments: List<String>,
    discussionMessages: List<String>,
) {
    ViewPager(
        noItems = 2,
        selectedPage = pageSelected,
        onPageChanged = onPageSelected,
    ) { index, _ ->
        if (index == 0)
            CommentsTab(comments)
        else
            DiscussionTab(discussionMessages)
    }
}

@Composable
fun LoadingDatingViewPage(
    pageSelected: Int,
    onPageSelected: OnSelected,
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
private fun CommentsTab(
    comments: List<String>
) {
    LazyColumnFor(
        modifier = Modifier.fillMaxWidth(),
        items = comments,
    )
    { item ->
        Text(text = item, modifier = Modifier.padding(Dimen.padding.big))
    }
}

@Composable
fun DiscussionTab(discussionMessages: List<String>) {
    LazyColumnFor(
        modifier = Modifier.fillMaxWidth(),
        items = discussionMessages,
    )
    { item ->
        Text(text = item, modifier = Modifier.padding(Dimen.padding.big))
    }
}