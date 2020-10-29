package com.example.movieappcompose.ui

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.example.movieappcompose.R

// Set of Material typography styles to start with
val typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)

private val appFontFamily = fontFamily(
    fonts = listOf(
        ResourceFont(
            resId = R.font.itc_avant_garde_std_bk,
            weight = FontWeight.Light,
            style = FontStyle.Normal
        ),
        ResourceFont(
            resId = R.font.itc_avant_garde_std_bk_obl,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        ResourceFont(
            resId = R.font.itc_avant_garde_std_md,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        ResourceFont(
            resId = R.font.itc_avant_garde_std_md_obl,
            weight = FontWeight.Normal,
            style = FontStyle.Italic
        ),
        ResourceFont(
            resId = R.font.itc_avant_garde_std_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        ResourceFont(
            resId = R.font.itc_avant_garde_std_bold_obl,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
    )
)

val ethnocentricFont = fontFamily(
    fonts = listOf(
        ResourceFont(
            resId = R.font.ethnocentric,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        )
    )
)

private val defaultTypography = Typography()
val appTypography = Typography(
    h1 = defaultTypography.h1.copy(fontFamily = appFontFamily),
    h2 = defaultTypography.h2.copy(fontFamily = appFontFamily),
    h3 = defaultTypography.h3.copy(fontFamily = appFontFamily),
    h4 = defaultTypography.h4.copy(fontFamily = appFontFamily),
    h5 = defaultTypography.h5.copy(fontFamily = appFontFamily),
    h6 = defaultTypography.h6.copy(
        fontFamily = appFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = Dimen.titleTextSize,
        color = Color.Black
    ),
    subtitle1 = defaultTypography.subtitle1.copy(
        fontFamily = appFontFamily,
        fontSize = Dimen.detailTextSize,
        color = MovieColors.detailText,
    ),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = appFontFamily),
    body1 = defaultTypography.body1.copy(fontFamily = appFontFamily),
    body2 = defaultTypography.body2.copy(fontFamily = appFontFamily),
    button = defaultTypography.button.copy(fontFamily = appFontFamily),
    caption = defaultTypography.caption.copy(fontFamily = appFontFamily),
    overline = defaultTypography.overline.copy(fontFamily = appFontFamily)
)