package com.example.movieappcompose.screens.actors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.movieappcompose.R
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.utlis.imageWidth500Url
import com.example.movieappcompose.widgets.Center
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun PersonItem(person: Person) {
    Row(
        Modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            data = imageWidth500Url(person.profilePicture),
            contentDescription = stringResource(id = R.string.actor_photo_description, person.name),
            modifier = Modifier
                    .weight(1.5f)
                    .padding(Dimen.padding.big)
                    .aspectRatio(Dimen.squareRatio)
                    .clip(
                        CircleShape
                    ),
            error = {
                Center {
                    Box(
                        modifier = Modifier
                                .fillMaxSize()
                                .background(MovieColors.backgroundEnd),
                    )
                }
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
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                    .weight(5f)
                    .fillMaxHeight()
                    .padding(Dimen.padding.medium),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = person.name,
                style = MaterialTheme.typography.h4.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = person.position,
                style = MaterialTheme.typography.h5.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center,
            )
        }
    }
}