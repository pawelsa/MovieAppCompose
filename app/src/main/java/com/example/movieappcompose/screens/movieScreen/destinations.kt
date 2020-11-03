package com.example.movieappcompose.screens.movieScreen

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.movieappcompose.ui.Navigator
import kotlinx.android.parcel.Parcelize

/**
 * Models the screens in the app and any arguments they require.
 */
sealed class Destination : Parcelable {
    @Parcelize
    object Home : Destination()

    @Immutable
    @Parcelize
    data class MovieDetail(val movieId: Long) : Destination()

    @Immutable
    @Parcelize
    data class SelectingMovieSeat(val movieId: Long) : Destination()

    @Immutable
    @Parcelize
    data class MovieTicket(val movieId: Long):Destination()
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navigator: Navigator<Destination>) {
    val selectMovie: (Long) -> Unit = { movieId: Long ->
        navigator.navigate(Destination.MovieDetail(movieId))
    }
    val selectMovieSeat: (Long) ->Unit = { movieId ->
        navigator.navigate(Destination.SelectingMovieSeat(movieId))
    }
    val showTicket:(Long)->Unit={ movieId ->
        navigator.navigate(Destination.MovieTicket(movieId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}