package com.example.movieappcompose.data.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val grade: Double,
    val overview: String = "",
    val releaseDate: String,
    val popularity: Double,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val crew: List<Crew>
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