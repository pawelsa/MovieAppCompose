package com.example.movieappcompose.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movieappcompose.data.models.movie.Genre
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors


@Composable
fun Genres(genreList: List<Genre>) {
    OverflowRow(
        modifier = Modifier
                .padding(
                    top = Dimen.padding.medium,
                    bottom = Dimen.padding.medium,
                ),
        spacing = Dimen.padding.small,
        children = {
            for (genre in genreList) {
                GenreTag(name = genre.name, color = MovieColors.yellow)
            }
        },
        overflow = {
            GenreTag(name = "...", color = MovieColors.yellow)
        })

}

@Composable
fun GenreTag(name: String, color: Color) {
    val tagTextStyle = MaterialTheme.typography.body2.copy(
        fontSize = Dimen.text.genre,
    )
    Text(
        text = name,
        modifier = Modifier
                .genreTag(color),
        style = tagTextStyle.copy(
            color = color
        )
    )
}

private fun Modifier.genreTag(color: Color) =
    this
            .border(
                width = Dimen.tagBorder,
                color = color,
                shape = RoundedCornerShape(Dimen.corner.tag)
            )
            .padding(horizontal = Dimen.padding.small, vertical = Dimen.padding.tiny)
