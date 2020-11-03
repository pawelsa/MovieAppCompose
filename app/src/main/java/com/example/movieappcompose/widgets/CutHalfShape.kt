package com.example.movieappcompose.widgets

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density


enum class CutHalfShapeType { TOP, BOTTOM, RIGHT, LEFT }

/**
 * A shape describing the rectangle with cut one of the halves
 *
 * @param cutHalfShapeType describes which part should be cut off
 */
class CutHalfShape(
    private val cutHalfShapeType: CutHalfShapeType
) : Shape {

    override fun createOutline(size: Size, density: Density): Outline = Outline.Generic(
        Path().apply {
            var x = if (cutHalfShapeType == CutHalfShapeType.LEFT) size.width / 2f else 0f
            var y = if (cutHalfShapeType == CutHalfShapeType.TOP) size.height / 2f else 0f

            moveTo(x, y)
            x = if (cutHalfShapeType == CutHalfShapeType.RIGHT) size.width / 2f else size.width
            lineTo(x, y)
            y = if (cutHalfShapeType == CutHalfShapeType.BOTTOM) size.height / 2f else size.height
            lineTo(x, y)
            x = if (cutHalfShapeType == CutHalfShapeType.LEFT) size.width / 2f else 0f
            lineTo(x, y)
            close()
        }
    )
}