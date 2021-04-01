package com.example.movieappcompose.data.models.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Crew(
    val department: String,
    val job: String,
    val name: String,
    val profilePicture: String
) : Parcelable