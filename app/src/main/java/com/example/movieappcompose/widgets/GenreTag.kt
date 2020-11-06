package com.example.movieappcompose.widgets

import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors


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
        for (genre in genreList.take(3)) {
            GenreTag(name = genre, color = MovieColors.yellow)
        }
        if (genreList.size > 3) {
            GenreTag(name = "...", color = MovieColors.yellow)
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
