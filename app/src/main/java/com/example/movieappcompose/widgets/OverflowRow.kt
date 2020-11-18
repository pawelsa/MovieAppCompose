package com.example.movieappcompose.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.movieappcompose.extensions.skipLast

enum class OverflowRowAlignment { Start, End, Center, SpaceBetween, SpaceEvenly }

@Composable
fun OverflowRow(
    modifier: Modifier = Modifier,
    alignment: OverflowRowAlignment = OverflowRowAlignment.Start,
    spacing: Dp = 0.dp,
    children: @Composable () -> Unit,
    overflow: @Composable () -> Unit,
) {
    val childrenWithOverflow: @Composable () -> Unit = {
        children()
        overflow()
    }
    Layout(
        children = childrenWithOverflow,
        modifier = modifier
    ) { measurables, constraints ->
        val maxWidth = constraints.maxWidth
        val spacingPx = spacing.toIntPx()

        val toDisplay = mutableListOf<Placeable>()
        var currentMainAxisSize: Int

        val childConstraints = Constraints(maxWidth = maxWidth)

        fun List<Placeable>.overflow(): Placeable = this.last()

        val placeables = measurables.map { it.measure(childConstraints) }
        val widthOfAllPlaceablesButOverflow = placeables
                .skipLast(1)
                .sumBy { it.width + spacingPx } - spacingPx

        if (widthOfAllPlaceablesButOverflow > maxWidth) {
            currentMainAxisSize = placeables.overflow().width
            for (placeable in placeables.skipLast(1)) {
                if (currentMainAxisSize + placeable.width + spacingPx < maxWidth) {
                    toDisplay.add(placeable)
                    currentMainAxisSize += placeable.width + spacingPx
                }
            }
            toDisplay.add(placeables.overflow())
        } else {
            currentMainAxisSize = widthOfAllPlaceablesButOverflow
            toDisplay.addAll(placeables.skipLast(1))
        }

        val mainAxisLayoutSize = currentMainAxisSize
        val crossAxisLayoutSize =
            toDisplay.maxByOrNull { it.height }?.height ?: constraints.maxHeight
        val leftOverSpace = maxWidth - mainAxisLayoutSize

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            var width = when (alignment) {
                OverflowRowAlignment.Center -> leftOverSpace / 2
                OverflowRowAlignment.End -> leftOverSpace
                OverflowRowAlignment.SpaceEvenly -> leftOverSpace / (toDisplay.size + 1)
                else -> 0
            }
            val spacer = when (alignment) {
                OverflowRowAlignment.SpaceEvenly -> leftOverSpace / (toDisplay.size + 1)
                OverflowRowAlignment.SpaceBetween -> leftOverSpace / (toDisplay.size - 1)
                else -> spacingPx
            }
            toDisplay.forEachIndexed { index, placeable ->
                placeable.placeRelative(width, 0)
                width += placeable.width + if (index != toDisplay.lastIndex) spacer else 0
            }
        }
    }
}
