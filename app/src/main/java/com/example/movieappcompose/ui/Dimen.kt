package com.example.movieappcompose.ui

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimen{
    val margin = Margin
    val padding = Padding
    val corner = Corner
    val text = Text
    val elevation = Elevation

    val tabProgressIndicator = 10.dp
    const val posterAspectRatio = 0.69f
    const val squareRatio = 1.0f
    val pillHeight = 5.dp
    val pillWidth = 40.dp
    val posterWidth = 160.dp
    val spacerMedium = 12.dp
    val tabProgressIndicatorStroke = 3.dp
    val tabBarBorderSpacer = 16.dp
    val starSize = 16.dp
    val spaceFromTopOfDetailScreen = 70.dp
    val bottomBarHeight = 48.dp.plus(padding.medium.times(2))
    val tagBorder = 1.dp
    val backLayerPeekHeight = 56.dp * 1.5f
    val frontLayerPeekHeight = 48.dp * 1.85f

    object Margin{
        val small = 4.dp
        val medium = 8.dp
        val big = 16.dp
    }

    object Padding{
        val tiny = 2.dp
        val small = 4.dp
        val medium = 8.dp
        val big = 16.dp
        val button = 12.dp
    }

    object Corner{
        val pill = 2.5.dp
        val tag = 5.dp
        val bigCard = 15.dp
        val big = 40.dp
        val bottomSheet = 30.dp
        val card = 10.dp
        val poster = 10.dp
    }

    object Elevation{
        val poster = 25.dp
        val button = 8.dp
    }

    object Text{
        val noDiscussionData = 60.sp
        val genre = 10.sp
        val role = 12.sp
        val name = 14.sp
        val button = 17.sp
        val detail = 14.sp
        val title = 20.sp
        val description = 17.sp
    }
}

