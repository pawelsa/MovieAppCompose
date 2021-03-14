package com.example.movieappcompose.screens.movieDetail.frontLayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnSelected
import com.example.movieappcompose.data.models.Discussion
import com.example.movieappcompose.data.models.Review
import com.example.movieappcompose.screens.movieDetail.MovieDetailState
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Center
import com.example.movieappcompose.widgets.MovieTabRow
import com.example.movieappcompose.widgets.pager_temp.ExperimentalPagerApi
import com.example.movieappcompose.widgets.pager_temp.HorizontalPager
import com.example.movieappcompose.widgets.pager_temp.PagerState
import com.example.movieappcompose.widgets.pager_temp.rememberPagerState
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FrontLayer(
    pageSelected: Int,
    onPageSelected: OnSelected,
    movieDetailState: MovieDetailState,
) {

    val pagerState = rememberPagerState(
        pageCount = 2,
        pageChanged = onPageSelected,
        initialPage = pageSelected,
    )

    val coroutineScope = rememberCoroutineScope()
    val onSelected: OnSelected = {
        coroutineScope.launch {
            pagerState.animateScrollToPage(it)
        }
        onPageSelected(it)
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                    .padding(vertical = Dimen.padding.big)
                    .requiredHeight(Dimen.pillHeight)
                    .requiredWidth(Dimen.pillWidth)
                    .clip(RoundedCornerShape(Dimen.corner.pill))
                    .background(MovieColors.greyPill)
                    .align(Alignment.CenterHorizontally),
        ) {}
        Spacer(
            modifier = Modifier
                    .requiredHeight(Dimen.margin.medium)
                    .fillMaxWidth()
        )
        when (movieDetailState) {
            is MovieDetailState.LoadedMovieDetails -> DataLoadedViewPager(
                pagerState = pagerState,
                onPageSelected = onSelected,
                reviews = movieDetailState.movie.reviews,
                discussionMessages = movieDetailState.movie.discussion
            )
            else -> LoadingDatingViewPage(
                pagerState = pagerState,
                onPageSelected = onSelected
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DataLoadedViewPager(
    pagerState: PagerState,
    onPageSelected: OnSelected,
    reviews: List<Review>,
    discussionMessages: List<Discussion>,
) {
    DiscussionTabRow(
        pageSelected = pagerState.currentPage,
        onPageSelected = onPageSelected,
        noReview = reviews.size,
        noDiscussion = discussionMessages.size
    )
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> ReviewsTab(reviews)
            else -> DiscussionTab(discussionMessages)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoadingDatingViewPage(
    pagerState: PagerState,
    onPageSelected: OnSelected,
) {
    DiscussionTabRow(
        pageSelected = pagerState.currentPage,
        onPageSelected = onPageSelected,
        isLoading = true
    )
    HorizontalPager(state = pagerState) {
        Center {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun DiscussionTabRow(
    pageSelected: Int,
    onPageSelected: OnSelected,
    isLoading: Boolean = false,
    noReview: Int = 0,
    noDiscussion: Int = 0,
) {
    MovieTabRow(pageSelected = pageSelected) {
        Tab(
            selected = pageSelected == 0,
            onClick = { onPageSelected(0) },
            modifier = Modifier.padding(Dimen.padding.small)
        ) {
            TabText(
                title = stringResource(id = R.string.detail_reviews),
                isLoading = isLoading,
                count = noReview,
                isSelected = pageSelected == 0
            )
        }
        Tab(
            selected = pageSelected == 1,
            onClick = { onPageSelected(1) },
            modifier = Modifier.padding(Dimen.padding.small)
        ) {
            TabText(
                title = stringResource(id = R.string.detail_discuss),
                isLoading = isLoading,
                count = noDiscussion,
                isSelected = pageSelected == 1
            )
        }
    }
}

@Composable
private fun TabText(title: String, isLoading: Boolean, count: Int, isSelected: Boolean) {
    val selectedTabTextStyle = MaterialTheme.typography.h2
    val notSelectedTabTextStyle =
        MaterialTheme.typography.h2.copy(color = MovieColors.nonSelectedText)
    val textStyle = if (isSelected) selectedTabTextStyle else notSelectedTabTextStyle

    Row {
        Text(
            text = title,
            style = textStyle
        )
        Spacer(modifier = Modifier.requiredWidth(Dimen.padding.small))
        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = Dimen.tabProgressIndicatorStroke,
                modifier = Modifier
                        .padding(bottom = Dimen.padding.medium)
                        .requiredSize(Dimen.tabProgressIndicator)
            )
        } else {
            Text(
                text = count.toString(),
                modifier = Modifier.padding(bottom = Dimen.padding.medium),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
private fun ReviewsTab(
    reviews: List<Review>,
) {
    TabContent(
        data = reviews,
        emptyMessage = stringResource(id = R.string.detail_no_reviews)
    ) { review ->
        Card(Modifier.padding(Dimen.padding.big)) {
            Column(
                Modifier
                        .padding(Dimen.padding.big)
                        .fillMaxWidth()
            ) {
                Text(text = review.author, style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.requiredHeight(Dimen.margin.medium))
                Text(text = review.content, style = MaterialTheme.typography.h5)
            }
        }
    }
}

@Composable
fun DiscussionTab(discussionMessages: List<Discussion>) {
    TabContent(
        discussionMessages,
        emptyMessage = stringResource(id = R.string.detail_no_discussions)
    ) { message ->
        Text(text = message.content, modifier = Modifier.padding(Dimen.padding.big))
    }
}

@Composable
fun <T> TabContent(
    data: List<T>,
    emptyMessage: String,
    content: @Composable LazyItemScope.(T) -> Unit,
) {
    if (data.isEmpty()) {
        ContentEmpty(emptyMessage)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(data, itemContent = content)
        }
    }
}

@Composable
private fun ContentEmpty(text: String) {
    Center {
        Text(
            text = text,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            lineHeight = (Dimen.text.noDiscussionData.value * 1.5).sp,
            modifier = Modifier.padding(Dimen.padding.big)
        )
    }
}