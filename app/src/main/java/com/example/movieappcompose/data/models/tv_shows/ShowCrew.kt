package com.example.movieappcompose.data.models.tv_shows

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowCrew(
    val name: String,
    val department: String,
    val job: String,
    val profilePath: String,
) : Parcelable
