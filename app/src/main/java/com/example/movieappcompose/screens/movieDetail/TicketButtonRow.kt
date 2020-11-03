package com.example.movieappcompose.screens.movieDetail

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors


@Composable
fun TicketButtons(
    onCollectPressed: OnClick,
    onBuyPressed: OnClick,
) {
    Row {
        // TODO: 02/11/2020 make this buttons as widgets
        Button(
            modifier = Modifier.weight(1f).padding(Dimen.paddingMedium),
            backgroundColor = MovieColors.greyButton,
            contentPadding = PaddingValues(Dimen.buttonPadding),
            elevation = Dimen.buttonElevation,
            onClick = onCollectPressed,
        ) {
            Text(
                text = stringResource(id = R.string.detail_collect),
                style = MaterialTheme.typography.button.copy(color = MovieColors.greyButtonText)
            )
        }
        Button(
            modifier = Modifier.weight(2f).padding(Dimen.paddingMedium),
            backgroundColor = MovieColors.yellow,
            contentPadding = PaddingValues(Dimen.buttonPadding),
            elevation = Dimen.buttonElevation,
            onClick = onBuyPressed,
        ) {
            Text(text = stringResource(id = R.string.detail_buy_now))
        }
    }
}
