package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors


@Composable
fun TicketButtons(
    isCollected: Boolean,
    onCollectPressed: OnClick,
    onBuyPressed: OnClick,
) {
    Row {
        // TODO: 02/11/2020 make this buttons as widgets
        Button(
            modifier = Modifier
                    .weight(1f)
                    .padding(Dimen.padding.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if(isCollected) MovieColors.greyButton else MovieColors.yellow
            ),
            contentPadding = PaddingValues(Dimen.padding.button),
            elevation = ButtonDefaults.elevation(Dimen.elevation.button),
            onClick = onCollectPressed,
        ) {
            Text(
                text = stringResource(id = if(isCollected) R.string.detail_collected else R.string.detail_collect),
                style = MaterialTheme.typography.button.copy(color = if(isCollected) MovieColors.greyButtonText else Color.White)
            )
        }
        Button(
            modifier = Modifier
                    .weight(2f)
                    .padding(Dimen.padding.medium),
            colors = ButtonDefaults.buttonColors(backgroundColor = MovieColors.yellow),
            contentPadding = PaddingValues(Dimen.padding.button),
            elevation = ButtonDefaults.elevation(Dimen.elevation.button),
            onClick = onBuyPressed,
        ) {
            Text(text = stringResource(id = R.string.detail_buy_now))
        }
    }
}