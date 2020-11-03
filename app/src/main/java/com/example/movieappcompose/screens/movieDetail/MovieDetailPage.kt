package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.movieDetail.backLayer.MovieDetailBackLayer
import com.example.movieappcompose.screens.movieDetail.frontLayer.FrontLayer
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Page

@ExperimentalMaterialApi
@Composable
fun MovieDetailPage(
    movieId: Long,
    viewModel: MovieDetailViewModel = viewModel(),
) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    onCommit(movieId) {
        viewModel.setMovie(movieId)
    }
    Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
        BackdropScaffold(
            appBar = {},
            peekHeight = Dimen.backLayerPeekHeight,
            headerHeight = Dimen.frontLayerPeekHeight,
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            frontLayerShape = RoundedCornerShape(
                topLeft = Dimen.corner.bottomSheet,
                topRight = Dimen.corner.bottomSheet
            ),
            backLayerBackgroundColor = MovieColors.backgroundEnd,
            backLayerContent = { MovieDetailBackLayer(viewModel.state) },
            frontLayerContent = { FrontLayer(viewModel.state) }
        )
    }
}
