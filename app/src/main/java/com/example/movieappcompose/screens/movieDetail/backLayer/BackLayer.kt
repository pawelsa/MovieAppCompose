package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bumptech.glide.request.RequestOptions
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.data.models.DetailedMovie
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.screens.movieDetail.MovieDetailState
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.utlis.imageWidth500Url
import com.example.movieappcompose.widgets.Center
import dev.chrisbanes.accompanist.glide.GlideImage
import jp.wasabeef.glide.transformations.BlurTransformation


@Composable
fun MovieDetailBackLayer(movieDetailState: MovieDetailState) {

    val onMorePressed: OnClick = {}

    when (movieDetailState) {
        is MovieDetailState.Init -> Center {
            CircularProgressIndicator()
        }
        is MovieDetailState.LoadedMovie -> LoadedMovieDetails(
            onMorePressed = onMorePressed,
            detailedMovie = movieDetailState.movie
        )
        is MovieDetailState.LoadedMovieDetails -> LoadedMovieDetails(
            onMorePressed = onMorePressed,
            detailedMovie = movieDetailState.movie
        )
    }
}

@Composable
fun LoadedMovieDetails(onMorePressed: OnClick, detailedMovie: DetailedMovie) {
    val movie = detailedMovie.movie
    BoxWithConstraints {
        Box(
            Modifier.fillMaxSize()
        ) {
            GlideImage(
                data = imageWidth500Url(movie.posterPath),
                modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(this@BoxWithConstraints.maxHeight / 2),
                contentDescription = stringResource(id = R.string.poster_description, movie.title),
                contentScale = ContentScale.Crop,
                requestBuilder = {
                    val options = RequestOptions()
                    options.transform(BlurTransformation(20))
                    apply(options)
                }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                MovieDetailAppBar(onMorePressed = onMorePressed)
                MovieDetailCard(movie = detailedMovie)
            }
        }
    }
}

@Composable
private fun MovieDetailCard(movie: DetailedMovie) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.requiredHeight(Dimen.spaceFromTopOfDetailScreen))
        ConstraintLayout(
            Modifier.fillMaxSize()
        ) {
            val (poster, info, content, body) = createRefs()

            Surface(
                elevation = Dimen.elevation.poster,
                modifier = Modifier
                        .zIndex(4.dp.value)
                        .requiredWidth(Dimen.posterWidth)
                        .aspectRatio(0.73f)
                        .clip(RoundedCornerShape(Dimen.corner.poster))
                        .constrainAs(poster) {
                            start.linkTo(body.start, margin = Dimen.margin.big)
                            bottom.linkTo(content.top, margin = Dimen.margin.big)
                        }) {
                GlideImage(
                    imageWidth500Url(movie.movie.posterPath),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(id = R.string.poster_description,
                        movie.movie.title),
                    modifier = Modifier.zIndex(5.dp.value)
                )
            }

            Box(
                Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = Dimen.corner.bigCard,
                                topEnd = Dimen.corner.bigCard,
                            )
                        )
                        .zIndex(3.dp.value)
                        .background(
                            Brush.Companion.linearGradient(colors = listOf(MovieColors.backgroundStart,
                                MovieColors.backgroundEnd)
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
                        },
                horizontalAlignment = Alignment.Start
            ) {
                GeneralMovieInfo(movie = movie.movie)
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
                DetailsPart(movie = movie)
            }
        }
    }
}