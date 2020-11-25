package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.data.models.Cast
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.utlis.imageWidth500Url
import dev.chrisbanes.accompanist.glide.GlideImage


@Composable
fun ActorsSection(onShowMorePressed: OnClick, actors: List<Cast>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.detail_actors),
            style = MaterialTheme.typography.h2
        )
        TextButton(
            onClick = onShowMorePressed,
            colors = ButtonConstants.defaultButtonColors(
                contentColor = MovieColors.greyText,
                backgroundColor = Color.Transparent
            ),
        ) {
            Text(
                text = stringResource(id = R.string.detail_see_more),
                style = MaterialTheme.typography.body1
            )
            Icon(Icons.Sharp.ArrowForward)
        }
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        for (actor in actors.take(4)) {
            ActorDetail(
                modifier = Modifier
                        .padding(Dimen.padding.medium)
                        .weight(1f),
                actor = actor,
            )
        }
    }
}

@Composable
fun ActorDetail(modifier: Modifier, actor: Cast) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        GlideImage(
            data = imageWidth500Url(actor.profilePicture),
            modifier = Modifier
                    .padding(Dimen.padding.medium)
                    .aspectRatio(Dimen.squareRatio)
                    .clip(
                        CircleShape
                    ),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = actor.name,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
        Text(
            text = actor.character,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
        )
    }
}