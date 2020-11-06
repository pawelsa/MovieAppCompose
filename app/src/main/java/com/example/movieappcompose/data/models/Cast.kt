package com.example.movieappcompose.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val character: String,
    val name: String,
    val order: Int,
    val profilePicture: String
) : Parcelable