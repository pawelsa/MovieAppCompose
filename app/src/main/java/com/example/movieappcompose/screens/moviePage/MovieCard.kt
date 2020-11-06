package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.utlis.imageWidth500Url
import com.example.movieappcompose.widgets.Genres
import com.example.movieappcompose.widgets.GlideImage

@Composable
fun MovieCard(movie: Movie, onClick: OnClick = {}) {
    WithConstraints {
        ConstraintLayout(
            Modifier
                    .padding(maxWidth.times(0.03f))
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onClick)
                    .padding(maxWidth.times(0.03f))
        ) {
            val (poster, card, info) = createRefs()

            Box(modifier = Modifier
                    .width(maxWidth.times(0.24f))
                    .zIndex(5.dp.value)
                    .aspectRatio(Dimen.posterAspectRatio)
                    .clip(RoundedCornerShape(Dimen.corner.poster))
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                        start.linkTo(card.start, margin = maxWidth.times(0.045f))
                        bottom.linkTo(info.bottom, margin = Dimen.margin.big)
                    }) {
                // TODO: 05/11/2020 add placeholder and loading image
                GlideImage(
                    imageWidth500Url(movie.posterPath),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .zIndex(4.dp.value)
                        .background(Color.White)
                        .constrainAs(card) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(info.top)
                            bottom.linkTo(info.bottom)
                            height = Dimension.fillToConstraints
                        }
            )

            Column(Modifier
                    .padding(vertical = 15.dp)
                    .zIndex(5.dp.value)
                    .constrainAs(info) {
                        start.linkTo(poster.end, margin = maxWidth.times(0.045f))
                        end.linkTo(card.end, margin = Dimen.margin.medium)
                        bottom.linkTo(poster.bottom)
                        width = Dimension.fillToConstraints
                    }) {
                TitleWithGrade(movie)
                Genres(genreList = movie.genres)
                ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
                    Text(
                        text = stringResource(
                            id = R.string.director,
                            movie.director
                        )
                    )
                    Text(
                        text = stringResource(
                            id = R.string.starring,
                            movie.starring
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleWithGrade(movie: Movie) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            Modifier
                    .weight(7f)
                    .fillMaxWidth(), alignment = Alignment.CenterStart
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = movie.title,
                style = MaterialTheme.typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            Modifier
                    .weight(1.5f)
                    .fillMaxWidth(), alignment = Alignment.CenterEnd
        ) {
            Text(
                textAlign = TextAlign.End,
                text = movie.grade.toString(),
                style = MaterialTheme.typography.h2.copy(color = MovieColors.yellow),
                maxLines = 1,
            )
        }
    }
}
