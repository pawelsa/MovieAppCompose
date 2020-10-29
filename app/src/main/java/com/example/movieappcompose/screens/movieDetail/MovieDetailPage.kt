package com.example.movieappcompose.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.screens.moviePage.MovieCard
import com.example.movieappcompose.widgets.Page

@Composable
fun MovieDetailPage(){
    val mainActivityViewModel: MainActivityViewModel = viewModel()
    Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
        MovieCard(title = "FightClub")
    }
}