package com.example.movieappcompose.screens.movieScreen

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.movieappcompose.data.models.movie.Movie
import com.example.movieappcompose.ui.Navigator
import kotlinx.parcelize.Parcelize

/**
 * Models the screens in the app and any arguments they require.
 */
sealed class MovieDestination : Parcelable {
    @Parcelize
    object Home : MovieDestination()

    @Immutable
    @Parcelize
    data class MovieDetail(val movie: Movie) : MovieDestination()

    @Immutable
    @Parcelize
    data class SelectingMovieSeat(val movieId: Long) : MovieDestination()

    @Immutable
    @Parcelize
    data class MovieTicket(val movieId: Long) : MovieDestination()

    @Immutable
    @Parcelize
    data class ActorsList(val movie: Movie) : MovieDestination()
}

/**
 * Models the navigation actions in the app.
 */
class MovieActions(navigator: Navigator<MovieDestination>) {
    val selectMovie: (Movie) -> Unit = { movie ->
        navigator.navigate(MovieDestination.MovieDetail(movie))
    }
    val selectMovieSeat: (Long) -> Unit = { movieId ->
        navigator.navigate(MovieDestination.SelectingMovieSeat(movieId))
    }
    val showTicket: (Long) -> Unit = { movieId ->
        navigator.navigate(MovieDestination.MovieTicket(movieId))
    }
    val showMoreActors: (Movie) -> Unit = { movie ->
        navigator.navigate(MovieDestination.ActorsList(movie))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}

