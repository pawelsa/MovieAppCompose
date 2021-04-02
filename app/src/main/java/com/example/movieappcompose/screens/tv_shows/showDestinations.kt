package com.example.movieappcompose.screens.tv_shows

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.movieappcompose.data.models.tv_shows.TvShow
import com.example.movieappcompose.ui.Navigator
import kotlinx.parcelize.Parcelize

sealed class ShowDestination : Parcelable {

    @Parcelize
    object Home : ShowDestination()

    @Immutable
    @Parcelize
    data class ShowDetail(val show: TvShow) : ShowDestination()

    @Immutable
    @Parcelize
    data class ActorsList(val show: TvShow) : ShowDestination()
}

class ShowActions(navigator: Navigator<ShowDestination>) {
    val selectShow: (TvShow) -> Unit = { show ->
        navigator.navigate(ShowDestination.ShowDetail(show))
    }
    val showMoreActors: (TvShow) -> Unit = { show ->
        navigator.navigate(ShowDestination.ActorsList(show))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}