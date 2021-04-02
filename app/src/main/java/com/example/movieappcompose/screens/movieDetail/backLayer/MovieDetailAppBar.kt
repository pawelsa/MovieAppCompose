package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.LocalMovieActions
import com.example.movieappcompose.widgets.MovieAppBar


@Composable
fun MovieDetailAppBar(
    onMorePressed: OnClick
) {
    val appBarIconModifier = Modifier
            .padding(Dimen.padding.medium)
            .requiredSize(32.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.3f))

    MovieAppBar(
        navigationIcon = {
            IconButton(
                onClick = LocalMovieActions.current.upPress,
            ) {
                Icon(
                    Icons.Sharp.ArrowBack,
                    "",
                    tint = Color.White,
                    modifier = appBarIconModifier
                )
            }
        },
        actions = {
            IconButton(
                onClick = onMorePressed
            ) {
                Icon(
                    Icons.Sharp.MoreVert,
                    "",
                    tint = Color.White,
                    modifier = Modifier
                            .graphicsLayer(rotationZ = 90f)
                            .then(appBarIconModifier),
                )
            }
        },
    )
}