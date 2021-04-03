package com.example.movieappcompose.screens.showDetail

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.showDetail.backLayer.TvShowDetailBackLayer
import com.example.movieappcompose.screens.showDetail.frontLayer.TvShowFrontLayer
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Page

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowDetailPage(
    tvShow: TvShow,
    viewModel: TvShowDetailViewModel = viewModel(),
) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()

    LaunchedEffect(key1 = tvShow.id) { viewModel.setShow(tvShow) }

    Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
        // TODO: 04/11/2020 fix the visible difference between back layer and front layer,
        //  when the front one is closed, we can see that back layer starts at the top of frontlayer,
        //  and is not on the bottom
        BackdropScaffold(
            appBar = {},
            peekHeight = Dimen.backLayerPeekHeight,
            headerHeight = Dimen.frontLayerPeekHeight,
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            frontLayerShape = RoundedCornerShape(
                topStart = Dimen.corner.bottomSheet,
                topEnd = Dimen.corner.bottomSheet
            ),
            backLayerBackgroundColor = MovieColors.backgroundEnd,
            backLayerContent = { TvShowDetailBackLayer(viewModel.state) },
            frontLayerContent = { TvShowFrontLayer(viewModel.state) }
        )
    }
}
