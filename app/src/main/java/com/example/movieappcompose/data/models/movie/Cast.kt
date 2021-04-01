package com.example.movieappcompose.data.models.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val character: String,
    val name: String,
    val order: Int,
    val profilePicture: String
) : Parcelable