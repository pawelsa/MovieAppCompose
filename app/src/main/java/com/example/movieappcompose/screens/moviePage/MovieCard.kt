package com.example.movieappcompose.screens.moviePage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.movieappcompose.R
import com.example.movieappcompose.base.OnClick
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun MovieCard(title: String, onClick: OnClick = {}) {
    WithConstraints {
        ConstraintLayout(
            Modifier
                .padding(maxWidth.times(0.03f))
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = onClick)
                .padding(maxWidth.times(0.03f))
        ) {
            val (poster, card, info) = createRefs()

            Box(modifier = Modifier
                .width(maxWidth.times(0.24f))
                .zIndex(5.dp.value)
                .aspectRatio(0.73f)
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(poster) {
                    top.linkTo(parent.top)
                    start.linkTo(card.start, margin = maxWidth.times(0.045f))
                    bottom.linkTo(info.bottom, margin = 15.dp)
                }) {
                Image(
                    asset = imageResource(id = R.drawable.fight_club),
                    contentScale = ContentScale.Crop
                )
            }

            Box(Modifier
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

            Column(Modifier
                .padding(vertical = 15.dp)
                .zIndex(5.dp.value)
                .constrainAs(info) {
                    start.linkTo(poster.end, margin = maxWidth.times(0.045f))
                    end.linkTo(card.end, margin = Dimen.marginMedium)
                    bottom.linkTo(poster.bottom)
                    width = Dimension.fillToConstraints
                }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = title, style = MaterialTheme.typography.h6)
                    Text(
                        text = "8.0",
                        style = MaterialTheme.typography.h6.copy(color = MovieColors.yellow)
                    )
                }
                Spacer(modifier = Modifier.height(Dimen.spacerMedium))
                ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
                    Text(text = "Director:")
                    Text(text = "Starring:")
                }
            }
        }
    }
}
