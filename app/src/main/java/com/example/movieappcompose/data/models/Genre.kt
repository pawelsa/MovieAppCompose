package com.example.movieappcompose.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val genre_id: Int,
    val name: String
) : Parcelable