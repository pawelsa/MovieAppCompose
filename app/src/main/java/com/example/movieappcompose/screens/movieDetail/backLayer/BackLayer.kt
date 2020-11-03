package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.WithConstraintsScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.screens.movieDetail.MovieDetailState
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.Center


@Composable
fun MovieDetailBackLayer(movieDetailState: MovieDetailState) {

    val onMorePressed: OnClick = {}

    when (movieDetailState) {
        is MovieDetailState.Init -> Center {
            CircularProgressIndicator()
        }
        is MovieDetailState.LoadedMovie -> LoadedMovieData(
            onMorePressed = onMorePressed,
            movie = "Fight club"
        )
        is MovieDetailState.LoadedMovieDetails -> LoadedMovieDetails(
            onMorePressed = onMorePressed,
            movie = "Fight club"
        )
    }
}

@Composable
fun LoadedMovieData(onMorePressed: OnClick, movie: String) {
    WithConstraints {
        Box(
            Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(maxHeight / 2)
                        .background(MovieColors.pageGradient(constraints.maxHeight.toFloat())),
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MovieDetailAppBar(onMorePressed)
                MovieDataCard(movie)
            }
        }
    }
}

@Composable
fun LoadedMovieDetails(onMorePressed: OnClick, movie: String) {
    WithConstraints {
        Box(
            Modifier.fillMaxSize()
        ) {
            Image(
                imageResource(id = R.drawable.fight_club),
                modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(maxHeight / 2),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MovieDetailAppBar(onMorePressed)
                MovieDetailCard(movie)
            }
        }
    }
}

@Composable
private fun WithConstraintsScope.MovieDetailCard(movie: String) {
    ScrollableColumn(
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(Dimen.spaceFromTopOfDetailScreen))
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (poster, info, content, body) = createRefs()

            Surface(
                elevation = Dimen.elevation.poster,
                modifier = Modifier
                        .width(Dimen.posterWidth)
                        .aspectRatio(0.73f)
                        .clip(RoundedCornerShape(Dimen.corner.poster))
                        .constrainAs(poster) {
                            start.linkTo(body.start, margin = Dimen.margin.big)
                            bottom.linkTo(content.top, margin = Dimen.margin.big)
                        }) {
                Image(
                    asset = imageResource(id = R.drawable.fight_club),
                    contentScale = ContentScale.Crop,
                )
            }


            val scale = DensityAmbient.current.density
            val dpAsPixels = (maxHeight.value * scale + 0.5f)
            Box(
                Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topLeft = Dimen.corner.bigCard,
                                topRight = Dimen.corner.bigCard,
                            )
                        )
                        .zIndex(4.dp.value)
                        .background(
                            VerticalGradient(
                                0.0f to MovieColors.backgroundStart,
                                1.0f to MovieColors.backgroundEnd,
                                startY = 0f,
                                endY = dpAsPixels,
                            )
                        )
                        .constrainAs(body) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(info.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
            )

            Column(
                Modifier
                        .padding(vertical = Dimen.padding.big)
                        .zIndex(5.dp.value)
                        .constrainAs(info) {
                            start.linkTo(poster.end, margin = Dimen.margin.big)
                            end.linkTo(body.end, margin = Dimen.margin.big)
                            bottom.linkTo(content.top)
                            width = Dimension.fillToConstraints
                        }
            ) {
                GeneralMovieInfo(movie = movie)
            }

            Column(modifier = Modifier
                    .zIndex(5.dp.value)
                    .constrainAs(content) {
                        start.linkTo(parent.start, margin = Dimen.margin.big)
                        end.linkTo(parent.end, margin = Dimen.margin.big)
                        bottom.linkTo(parent.bottom, margin = Dimen.margin.medium)
                        width = Dimension.fillToConstraints
                    }
            ) {
                // TODO: 03/11/2020 change to data from model
                DetailsPart(movie = "Fight club")
            }
        }
    }
}

@Composable
private fun WithConstraintsScope.MovieDataCard(movie: String) {
    ScrollableColumn(
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(Dimen.spaceFromTopOfDetailScreen))
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (poster, info, content, body) = createRefs()

            Surface(
                elevation = Dimen.elevation.poster,
                modifier = Modifier
                        .width(Dimen.posterWidth)
                        .aspectRatio(0.73f)
                        .clip(RoundedCornerShape(Dimen.corner.poster))
                        .background(MovieColors.greyPill)
                        .constrainAs(poster) {
                            start.linkTo(body.start, margin = Dimen.margin.big)
                            bottom.linkTo(content.top, margin = Dimen.margin.big)
                        }) { }


            val scale = DensityAmbient.current.density
            val dpAsPixels = (maxHeight.value * scale + 0.5f)
            Box(
                Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topLeft = Dimen.corner.bigCard,
                                topRight = Dimen.corner.bigCard,
                            )
                        )
                        .zIndex(4.dp.value)
                        .background(
                            VerticalGradient(
                                0.0f to MovieColors.backgroundStart,
                                1.0f to MovieColors.backgroundEnd,
                                startY = 0f,
                                endY = dpAsPixels,
                            )
                        )
                        .constrainAs(body) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(info.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
            )

            Column(
                Modifier
                        .padding(vertical = Dimen.padding.big)
                        .zIndex(5.dp.value)
                        .constrainAs(info) {
                            start.linkTo(poster.end, margin = Dimen.margin.big)
                            end.linkTo(body.end, margin = Dimen.margin.big)
                            bottom.linkTo(content.top)
                            width = Dimension.fillToConstraints
                        }
            ) {
                GeneralMovieInfo(movie = movie)
            }

            Column(modifier = Modifier
                    .zIndex(5.dp.value)
                    .constrainAs(content) {
                        start.linkTo(parent.start, margin = Dimen.margin.big)
                        end.linkTo(parent.end, margin = Dimen.margin.big)
                        bottom.linkTo(parent.bottom, margin = Dimen.margin.medium)
                        width = Dimension.fillToConstraints
                    }
            ) {
                LoadingDetailsPart()
            }
        }
    }
}