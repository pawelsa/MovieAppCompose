package com.example.movieappcompose.screens.actors

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.screens.mainActivity.MainActivityViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.widgets.MovieTabRow
import com.example.movieappcompose.widgets.Page
import com.example.movieappcompose.widgets.pager_temp.ExperimentalPagerApi
import com.example.movieappcompose.widgets.pager_temp.HorizontalPager
import com.example.movieappcompose.widgets.pager_temp.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ActorsPage(
    movie: Movie,
    mainActivityViewModel: MainActivityViewModel = viewModel(),
) {
    val tabs = listOf("Cast", "Crew")
    val pagerState = rememberPagerState(pageCount = tabs.size)
    val coroutineScope = rememberCoroutineScope()

    Page(showBottomBar = mainActivityViewModel.showBottomNavigationBar) {
        Column {
            Row(Modifier.padding(horizontal = Dimen.padding.medium, vertical = Dimen.padding.big),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Sharp.ArrowBack, "Go back")
                Spacer(modifier = Modifier.padding(start = Dimen.margin.big))
                Text(text = movie.title, style = MaterialTheme.typography.h2)
            }
            MovieTabRow(
                modifier = Modifier.padding(vertical = Dimen.padding.medium),
                tabs = tabs,
                pageSelected = pagerState.currentPage,
                onPageSelected = { coroutineScope.launch { pagerState.animateScrollToPage(it) } },
            ) {
                Text(
                    text = it,
                )
            }
            HorizontalPager(state = pagerState) { page ->
                val people by remember(page) {
                    mutableStateOf(if (page == 0) movie.cast.map {
                        Person(it.name,
                            it.profilePicture,
                            it.character)
                    } else movie.crew.map { Person(it.name, it.profilePicture, it.job) })
                }
                LazyColumn {
                    items(people) { person ->
                        PersonItem(person = person)
                    }
                }
            }
        }

    }
}

data class Person(
    val name: String,
    val profilePicture: String,
    val position: String,
)