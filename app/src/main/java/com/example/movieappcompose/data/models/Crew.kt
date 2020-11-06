package com.example.movieappcompose.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val department: String,
    val job: String,
    val name: String,
    val profilePicture: String
) : Parcelable