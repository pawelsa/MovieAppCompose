package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.movieDetail.backLayer.MovieDetailBackLayer
import com.example.movieappcompose.screens.movieDetail.frontLayer.FrontLayer
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Page

@ExperimentalMaterialApi
@Composable
fun MovieDetailPage(
    movie: Movie,
    viewModel: MovieDetailViewModel = viewModel(),
) {
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    /*onCommit(movie.id) {
        viewModel.setMovie(movie)
    }*/
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
            backLayerContent = { MovieDetailBackLayer(viewModel.state) },
            frontLayerContent = { FrontLayer(viewModel.state) }
        )
    }
}
