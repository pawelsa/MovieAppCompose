package com.example.movieappcompose.utlis

import com.example.movieappcompose.data.dataSources.api.ApiConsts

val imageWidth500Url = { path: String ->
    ApiConsts.imageBaseUrl + "w500" + path
}