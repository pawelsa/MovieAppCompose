package com.example.movieappcompose.data.models

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val posterPath: String,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val grade: Double,
    val overview: String,
    val releaseDate: String,
    val popularity: Double,
    val genres: List<String>,
    val cast: List<Cast>,
    val crew: List<Crew>
) : Parcelable {
    @IgnoredOnParcel
    val starring = cast
            .take(2)
            .map {
                it.name
                        .split(" ")
                        .last()
            }
            .joinToString(" / ")

    @IgnoredOnParcel
    val director = crew.first { it.job.toLowerCase() == "director" }.name
}