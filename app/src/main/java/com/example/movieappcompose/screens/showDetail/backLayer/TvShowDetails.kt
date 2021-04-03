package com.example.movieappcompose.screens.showDetail.backLayer

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.data.models.tv_shows.DetailedShow
import com.example.movieappcompose.screens.movieDetail.backLayer.TicketButtons
import com.example.movieappcompose.screens.showDetail.TvShowDetailViewModel
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.utlis.LocalShowActions


@Composable
fun TvShowDetailsPart(show: DetailedShow) {
    val tvShowDetailViewModel: TvShowDetailViewModel = viewModel()
    val actionAmbient = LocalShowActions.current
    var isDescriptionExtended by remember { mutableStateOf(false) }

    val onCollectPressed = {
//        tvShowDetailViewModel.changeCollectedStatus(show.tvShow)
    }
    val onBuyPressed = {
        // TODO: 03/11/2020 change to movieId
    }
    val onShowMoreActorsPressed = {
        // TODO: 03/11/2020 implement showing actors list
        actionAmbient.showMoreActors(show.tvShow)
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
        text = show.tvShow.overview,
        style = MaterialTheme.typography.h3,
        maxLines = if (isDescriptionExtended) 16 else 4,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(modifier = Modifier.requiredHeight(Dimen.margin.big))
    TicketButtons(
        isCollected = show.isCollected,
        onCollectPressed = onCollectPressed,
        onBuyPressed = onBuyPressed
    )
    Spacer(modifier = Modifier.requiredHeight(Dimen.margin.big))
    ActorsSection(onShowMorePressed = onShowMoreActorsPressed, show.tvShow.cast)
}
