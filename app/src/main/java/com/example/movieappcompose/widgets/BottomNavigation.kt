package com.example.movieappcompose.widgets

import androidx.compose.animation.animate
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun BottomNavigationBar(show: Boolean, current: Int, onSelect: (Int) -> Unit) {

    val modifier = Modifier.height(animate(target = if (show) Dimen.bottomBarHeight else 0.dp))
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topLeft = Dimen.bigCornerRadius,
                    topRight = Dimen.bigCornerRadius
                )
            )
            .background(Color.White)
            .padding(Dimen.paddingMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavigationBarItem(
                icon = Icons.Outlined.Home,
                isSelected = current == 0,
                onClick = { onSelect(0) },
            )
            BottomNavigationBarItem(
                icon = Icons.Outlined.Edit,
                isSelected = current == 1,
                onClick = { onSelect(1) },
            )
            BottomNavigationBarItem(
                icon = Icons.Outlined.Person,
                isSelected = current == 2,
                onClick = { onSelect(2) },
            )
        }
    }
}

@Composable
fun BottomNavigationBarItem(icon: VectorAsset, isSelected: Boolean, onClick: () -> Unit) {
    val iconColor = if (isSelected) MovieColors.yellow else MovieColors.nonSelectedText
    IconButton(onClick = onClick) {
        Icon(
            icon,
            tint = animate(
                target = iconColor,
                animSpec = tween(durationMillis = 250, easing = LinearEasing)
            )
        )
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val currentlySelected = remember { mutableStateOf(0) }
    BottomNavigationBar(true, currentlySelected.value) {
        currentlySelected.value = it
    }
}