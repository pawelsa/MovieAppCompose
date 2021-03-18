package com.example.movieappcompose.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.ui.Dimen
import com.example.movieappcompose.ui.MovieColors

@Composable
fun BottomNavigationBar(show: Boolean, current: Int, onSelect: (Int) -> Unit) {

    val height by animateDpAsState(targetValue = if (show) Dimen.bottomBarHeight else 0.dp)

    val modifier = Modifier
            .requiredHeight(height)
    Box(
        modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topStart = Dimen.corner.big,
                        topEnd = Dimen.corner.big,
                    )
                )
                .background(Color.White)
                .padding(Dimen.padding.medium)
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
fun BottomNavigationBarItem(icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MovieColors.yellow else MovieColors.nonSelectedText,
        animationSpec = tween(durationMillis = 250, easing = LinearEasing)
    )

    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon, contentDescription = "",
            tint = iconColor,
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