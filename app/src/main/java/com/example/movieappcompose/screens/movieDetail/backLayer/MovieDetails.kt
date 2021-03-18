package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.movieappcompose.R
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.LocalActions


@Composable
fun DetailsPart(movie: Movie) {
    val actionAmbient = LocalActions.current
    var isDescriptionExtended by remember { mutableStateOf(false) }

    val onCollectPressed = {
        // TODO: 03/11/2020 add some action
    }
    val onBuyPressed = {
        // TODO: 03/11/2020 change to movieId
        actionAmbient.selectMovieSeat(0)
    }
    val onShowMoreActorsPressed = {
        // TODO: 03/11/2020 implement showing actors list
        actionAmbient.showMoreActors(movie)
    }

    Text(
        text = stringResource(id = R.string.detail_introducion),
        style = MaterialTheme.typography.h2,
        modifier = Modifier.padding(vertical = Dimen.padding.medium)
    )
    Text(
        modifier = Modifier
                .clip(RoundedCornerShape(Dimen.corner.tag))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    isDescriptionExtended = true
                },
        text = movie.overview,
        style = MaterialTheme.typography.h3,
        maxLines = if (isDescriptionExtended) 16 else 4,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(modifier = Modifier.requiredHeight(Dimen.margin.big))
    TicketButtons(
        onCollectPressed = onCollectPressed,
        onBuyPressed = onBuyPressed
    )
    Spacer(modifier = Modifier.requiredHeight(Dimen.margin.big))
    ActorsSection(onShowMorePressed = onShowMoreActorsPressed, movie.cast)
}
