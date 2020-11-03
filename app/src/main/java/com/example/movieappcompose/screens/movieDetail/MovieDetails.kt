package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.movieappcompose.R
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.ActionsAmbient


@Composable
fun DetailsPart(movieId:Long) {
    val actionAmbient = ActionsAmbient.current

    val onCollectPressed = {
        // TODO: 03/11/2020 add some action
    }
    val onBuyPressed = {
        actionAmbient.selectMovieSeat(movieId)
    }
    val onShowMoreActorsPressed = {
        // TODO: 03/11/2020 implement showing actors list
    }

    Text(
        text = stringResource(id = R.string.detail_introducion),
        style = MaterialTheme.typography.h2,
        modifier = Modifier.padding(vertical = Dimen.padding.medium)
    )
    Text(
        text = stringResource(id = R.string.lorem_ipsum),
        style = MaterialTheme.typography.h3,
        maxLines = 4,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(modifier = Modifier.height(Dimen.margin.big))
    TicketButtons(
        onCollectPressed = onCollectPressed,
        onBuyPressed = onBuyPressed
    )
    Spacer(modifier = Modifier.height(Dimen.margin.big))
    ActorsSection(onShowMorePressed = onShowMoreActorsPressed)
}