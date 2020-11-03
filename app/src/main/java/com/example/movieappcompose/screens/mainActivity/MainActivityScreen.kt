package com.example.movieappcompose.screens.mainActivity

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.drawLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.movieappcompose.R
import com.example.movieappcompose.screens.moviePage.MoviePage
import com.example.movieappcompose.screens.movieScreen.MovieScreen
import com.example.movieappcompose.screens.scanner.ScannerScreen
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.widgets.BottomNavigationBar
import com.example.movieappcompose.widgets.Page


@ExperimentalMaterialApi
@Composable
fun MainScreen(
    mainActivityViewModel: MainActivityViewModel,
    backDispatcher: OnBackPressedDispatcher
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                show = mainActivityViewModel.showBottomNavigationBar,
                current = mainActivityViewModel.currentSelectedItemInNavigationBar,
                onSelect = { mainActivityViewModel.currentSelectedItemInNavigationBar = it }
            )
        }
    ) {
        Crossfade(current = mainActivityViewModel.currentSelectedItemInNavigationBar) { currentPage ->
            when (currentPage) {
                1 -> ScannerScreen(mainActivityViewModel.showBottomNavigationBar)
                2 -> Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) { }
                else -> MovieScreen(mainActivityViewModel, backDispatcher)
            }
        }
    }
}