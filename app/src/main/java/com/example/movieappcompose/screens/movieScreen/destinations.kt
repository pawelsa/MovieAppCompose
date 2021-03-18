package com.example.movieappcompose.screens.movieScreen

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.movieappcompose.data.models.Movie
import com.example.movieappcompose.ui.Navigator
import kotlinx.parcelize.Parcelize

/**
 * Models the screens in the app and any arguments they require.
 */
sealed class Destination : Parcelable {
    @Parcelize
    object Home : Destination()

    @Immutable
    @Parcelize
    data class MovieDetail(val movie: Movie) : Destination()

    @Immutable
    @Parcelize
    data class SelectingMovieSeat(val movieId: Long) : Destination()

    @Immutable
    @Parcelize
    data class MovieTicket(val movieId: Long) : Destination()

    @Immutable
    @Parcelize
    data class ActorsList(val movie: Movie) : Destination()
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navigator: Navigator<Destination>) {
    val selectMovie: (Movie) -> Unit = { movie ->
        navigator.navigate(Destination.MovieDetail(movie))
    }
    val selectMovieSeat: (Long) -> Unit = { movieId ->
        navigator.navigate(Destination.SelectingMovieSeat(movieId))
    }
    val showTicket: (Long) -> Unit = { movieId ->
        navigator.navigate(Destination.MovieTicket(movieId))
    }
    val showMoreActors: (Movie) -> Unit = { movie ->
        navigator.navigate(Destination.ActorsList(movie))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}