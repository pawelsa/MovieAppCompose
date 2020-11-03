package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors


@Composable
fun ActorsSection(onShowMorePressed: OnClick) {
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
            backgroundColor = Color.Transparent,
            contentColor = MovieColors.greyText,
        ) {
            Text(
                text = stringResource(id = R.string.detail_see_more),
                style = MaterialTheme.typography.body1
            )
            Icon(Icons.Sharp.ArrowForward)
        }
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        for (x in 0 until 4){
            ActorDetail(
                modifier = Modifier
                        .padding(Dimen.paddingMedium)
                        .weight(1f),
                name = "Jakiś gość",
                role = "Grał kogoś",
            )
        }
    }
}

@Composable
fun ActorDetail(modifier: Modifier, name:String, role:String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            asset = imageResource(id = R.drawable.fight_club),
            modifier = Modifier
                    .padding(Dimen.paddingMedium)
                    .aspectRatio(Dimen.squareRatio)
                    .clip(
                        CircleShape
                    ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h4,
        )
        Text(
            text = role,
            style = MaterialTheme.typography.h5,
        )
    }
}