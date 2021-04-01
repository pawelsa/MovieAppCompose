package com.example.movieappcompose.screens.movieDetail.backLayer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.movieappcompose.R
import com.example.movieappcompose.data.models.movie.Movie
import com.example.movieappcompose.extensions.roundTo
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors
import com.example.movieappcompose.widgets.CutHalfShape
import com.example.movieappcompose.widgets.CutHalfShapeType
import com.example.movieappcompose.widgets.Genres


@Composable
fun GeneralMovieInfo(movie: Movie) {
    Text(text = movie.title, style = MaterialTheme.typography.h2)
    Genres(movie.genres)
    ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
        Text(
            text = stringResource(
                id = R.string.director, formatArgs = arrayOf(
                    movie.director?.name ?: stringResource(
                        id = R.string.unknown
                    )
                )
            )
        )
        Spacer(modifier = Modifier.requiredHeight(Dimen.margin.small))
        Text(text = stringResource(id = R.string.starring, formatArgs = arrayOf(movie.starring)))
    }
    Spacer(modifier = Modifier.requiredHeight(Dimen.margin.medium))
    Grade(grade = movie.grade)
}

@Composable
fun Grade(grade: Double) {
    val fullStars = (grade / 2).toInt()
    val isHalfStar = grade % 2 != 0.0
    val starModifier = Modifier.requiredSize(Dimen.starSize)
    val ghostStarsMargin = Dimen.starSize * (5 - fullStars)
    val marginBetweenStarsAndGrade = Dimen.margin.medium + ghostStarsMargin
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (x in 0 until fullStars) {
            Icon(Icons.Filled.Star, "", tint = MovieColors.yellow, modifier = starModifier)
        }
        if (isHalfStar) {
            Icon(
                Icons.Filled.Star,
                "",
                tint = MovieColors.yellow,
                modifier = starModifier.clip(CutHalfShape(CutHalfShapeType.RIGHT))
            )
        }
        Spacer(modifier = Modifier.requiredWidth(marginBetweenStarsAndGrade))
        Text(
            text = grade
                    .roundTo(1)
                    .toString(),
            style = MaterialTheme.typography.h2.copy(
                color = MovieColors.yellow,
                fontSize = Dimen.text.description
            )
        )
    }
}