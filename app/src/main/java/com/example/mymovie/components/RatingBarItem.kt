package com.example.mymovie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun RatingBar(
    rating: Double,
    maxRating: Int = 5,
    filledColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    halfFilledColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    emptyColor: Color = Color.Gray
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 1..maxRating) {
            val icon = when {
                i <= rating -> Icons.Filled.Star
                i - 0.5f <= rating -> Icons.Filled.StarHalf
                else -> Icons.Outlined.Star
            }
            val starColor = when {
                i <= rating -> filledColor
                i - 0.5f <= rating -> halfFilledColor
                else -> emptyColor
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}