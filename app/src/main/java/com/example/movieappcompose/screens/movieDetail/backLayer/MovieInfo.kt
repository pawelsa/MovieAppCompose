package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.Icon
import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.extensions.roundTo
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.CutHalfShape
import com.example.movieappcompose.widgets.CutHalfShapeType


@Composable
fun GeneralMovieInfo(movie: String, grade: Int = 9) {
    Text(text = movie, style = MaterialTheme.typography.h2)
    Genres(listOf("Action", "Thiller"))
    ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
        Text(text = stringResource(id = R.string.director, formatArgs = arrayOf("")))
        Spacer(modifier = Modifier.height(Dimen.margin.small))
        Text(text = stringResource(id = R.string.starring, formatArgs = arrayOf("")))
    }
    Spacer(modifier = Modifier.height(Dimen.margin.medium))
    Grade(grade = grade)
}

@Composable
fun Genres(genreList: List<String>) {
    Row(
        Modifier
                .padding(
                    top = Dimen.padding.medium,
                    bottom = Dimen.padding.medium,
                    end = Dimen.padding.big
                )
                .fillMaxWidth()
    ) {
        for (genre in genreList){
            GenreTag(name = genre, color = MovieColors.yellow)
        }
    }
}

@Composable
fun GenreTag(name: String, color: Color) {
    val tagTextStyle = MaterialTheme.typography.body2.copy(
        fontSize = Dimen.text.genre,
    )
    Text(
        text = name,
        modifier = Modifier.genreTag(color),
        style = tagTextStyle.copy(
            color = color
        )
    )
}

private fun Modifier.genreTag(color: Color) =
    this
            .padding(end = Dimen.padding.medium)
            .border(
                width = Dimen.tagBorder,
                color = color,
                shape = RoundedCornerShape(Dimen.corner.tag)
            )
            .padding(horizontal = Dimen.padding.small, vertical = Dimen.padding.tiny)


@Composable
fun Grade(grade: Int) {
    val fullStars = grade / 2
    val isHalfStar = grade % 2 != 0
    val starModifier = Modifier.height(Dimen.starSize)
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (x in 0 until fullStars) {
            Icon(asset = Icons.Filled.Star, tint = MovieColors.yellow, modifier = starModifier)
        }
        if (isHalfStar) {
            Icon(
                asset = Icons.Filled.Star,
                tint = MovieColors.yellow,
                modifier = starModifier.clip(CutHalfShape(CutHalfShapeType.RIGHT))
            )
        }
        Spacer(modifier = Modifier.width(Dimen.margin.medium))
        Text(
            text = grade
                    .toDouble()
                    .roundTo(1)
                    .toString(),
            style = MaterialTheme.typography.h2.copy(
                color = MovieColors.yellow,
                fontSize = Dimen.text.description
            )
        )
    }
}