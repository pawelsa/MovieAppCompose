package com.example.movieappcompose.data.models.tv_shows

import android.os.Parcelable
import com.example.movieappcompose.data.models.movie.Genre
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val releaseDate: String,
    val genres: List<Genre>,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val grade: Double,
    val cast: List<ShowCast>,
    val crew: List<ShowCrew>,
) : Parcelable {

    @IgnoredOnParcel
    val starring
        get() = cast
                .take(2)
                .map {
                    it.name
                            .split(" ")
                            .last()
                }
                .joinToString(" / ")

    @IgnoredOnParcel
    val director
        get() = crew.firstOrNull {
            it.job
                    .toLowerCase()
                    .contains("director")
        }
}
