package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Page

@ExperimentalMaterialApi
@Composable
fun MovieDetailPage(movieId: Long) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
        BackdropScaffold(
            appBar = {},
            peekHeight = Dimen.backLayerPeekHeight,
            headerHeight = Dimen.frontLayerPeekHeight,
            scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
            frontLayerShape = RoundedCornerShape(
                topLeft = Dimen.bottomSheetCornerRadius,
                topRight = Dimen.bottomSheetCornerRadius
            ),
            backLayerBackgroundColor = MovieColors.backgroundEnd,
            backLayerContent = { MovieDetailBackLayer(movie = "Fight Club") },
            frontLayerContent = { FrontLayer() }
        )
    }
}



