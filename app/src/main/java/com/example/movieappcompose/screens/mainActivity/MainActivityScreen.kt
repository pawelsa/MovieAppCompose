package com.example.movieappcompose.screens.mainActivity

import androidx.compose.animation.Crossfade
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.movieappcompose.screens.moviePage.MoviePage
import com.example.movieappcompose.screens.scanner.ScannerScreen
import com.example.movieappcompose.widgets.BottomNavigationBar
import com.example.movieappcompose.widgets.Page


@Composable
fun MainScreen(mainActivityViewModel: MainActivityViewModel) {
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
                else -> MoviePage()
            }
        }
    }
}