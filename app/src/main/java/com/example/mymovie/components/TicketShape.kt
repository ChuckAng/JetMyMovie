package com.example.mymovie.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

class TicketShape(
    private val circleRadius: Dp,
    private val cornerSize: CornerSize,
    private val orientation: Int = ORIENTATION_VERTICAL
) : Shape {

    companion object {
        const val ORIENTATION_VERTICAL = 1
        const val ORIENTATION_HORIZONTAL = 2
    }

    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerSize.toPx(size, density)))
        val roundedRectPath = Path().apply { addRoundRect(roundedRect) }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(size, density)
        )
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val middleX =
            if (orientation == ORIENTATION_VERTICAL) size.width.div(other = 2) else size.height.div(
                2
            )
        val circleRadiusInPx = with(density) { circleRadius.toPx() }
        if (orientation == ORIENTATION_VERTICAL) {
            return Path().apply {
                reset()
                // Ensure we start drawing line at top left
                lineTo(x = 0F, y = 0F)
                // Draw line to top middle
                lineTo(middleX, y = 0F)
                // Draw top cutout
                arcTo(
                    rect = Rect(
                        left = middleX.minus(circleRadiusInPx),
                        top = 0F.minus(circleRadiusInPx),
                        right = middleX.plus(circleRadiusInPx),
                        bottom = circleRadiusInPx
                    ),
                    startAngleDegrees = 180F,
                    sweepAngleDegrees = -180F,
                    forceMoveTo = false
                )
                // Draw line to top right
                lineTo(x = size.width, y = 0F)
                // Draw line to bottom right
                lineTo(x = size.width, y = size.height)
                // Draw line to bottom middle
                lineTo(middleX, y = size.height)
                // Draw bottom cutout
                arcTo(
                    rect = Rect(
                        left = middleX.minus(circleRadiusInPx),
                        top = size.height - circleRadiusInPx,
                        right = middleX.plus(circleRadiusInPx),
                        bottom = size.height.plus(circleRadiusInPx)
                    ),
                    startAngleDegrees = 0F,
                    sweepAngleDegrees = -180F,
                    forceMoveTo = false
                )
                // Draw line to bottom left
                lineTo(x = 0F, y = size.height)
                // Draw line back to top left
                lineTo(x = 0F, y = 0F)
            }
        } else {
            return Path().apply {
                reset()
                lineTo(x = 0F, y = middleX)
                // draw left cutout
                arcTo(
                    rect = Rect(
                        left = -circleRadiusInPx,
                        top = middleX.minus(circleRadiusInPx),
                        right = circleRadiusInPx,
                        bottom = middleX.plus(circleRadiusInPx)
                    ),
                    startAngleDegrees = 270F,
                    sweepAngleDegrees = 180F,
                    forceMoveTo = false
                )

                lineTo(x = 0F, y = size.height)
                lineTo(x = size.width, y = size.height)
                lineTo(x = size.width, y = middleX)
                // draw right cutout
                arcTo(
                    rect = Rect(
                        left = size.width - circleRadiusInPx,
                        top = middleX - circleRadiusInPx,
                        right = size.width + circleRadiusInPx,
                        bottom = middleX + circleRadiusInPx
                    ),
                    startAngleDegrees = 90F,
                    sweepAngleDegrees = 180F,
                    forceMoveTo = false
                )
                lineTo(x = size.width, y = 0F)
                close()
            }
        }
    }
}