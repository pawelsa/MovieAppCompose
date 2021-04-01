package com.example.movieappcompose.data.models.tv_shows

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowCast(
    val character: String,
    val name: String,
    val order: Int,
    val profilePath: String,
) : Parcelable
