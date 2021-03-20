package com.example.movieappcompose.screens.actors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movieappcompose.R
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.utlis.imageWidth500Url
import dev.chrisbanes.accompanist.glide.GlideImage


@Composable
fun PersonItem(person: Person) {
    BoxWithConstraints {
        ConstraintLayout(
            Modifier
                    .padding(maxWidth.times(0.03f))
                    .clip(RoundedCornerShape(10.dp))
                    .padding(maxWidth.times(0.03f))
        ) {
            val (poster, card, info) = createRefs()

            Box(modifier = Modifier
                    .requiredWidth(this@BoxWithConstraints.maxWidth.times(0.2f))
                    .zIndex(5.dp.value)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .shadow(elevation = 10.dp, shape = CircleShape)
                    .constrainAs(poster) {
                        top.linkTo(parent.top)
                        start.linkTo(card.start,
                            margin = this@BoxWithConstraints.maxWidth.times(0.045f))
                        bottom.linkTo(info.bottom, margin = Dimen.margin.big)
                    }) {
                // TODO: 05/11/2020 add placeholder and loading image
                GlideImage(
                    data = imageWidth500Url(person.profilePicture),
                    contentDescription = stringResource(id = R.string.poster_description,
                        person.name),
                    contentScale = ContentScale.Crop,
                    error = {
                        Box(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .background(MovieColors.backgroundEnd),
                        )
                    },
                    loading = {
                        Box(
                            modifier = Modifier
                                    .fillMaxSize()
                                    .background(MovieColors.backgroundEnd),
                        ) {
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    },
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

            Column(
                Modifier
                        .padding(vertical = 15.dp)
                        .zIndex(5.dp.value)
                        .constrainAs(info) {
                            start.linkTo(poster.end,
                                margin = this@BoxWithConstraints.maxWidth.times(0.045f))
                            end.linkTo(card.end, margin = Dimen.margin.medium)
                            bottom.linkTo(poster.bottom)
                            width = Dimension.fillToConstraints
                        },
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = person.name,
                    style = MaterialTheme.typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = person.position,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}
