package com.flexcode.multiselectcalendar.compose_ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

/**
 * Shapes used across the entire app to ensure consistency
 * @param [extraSmall] 4.dp
 * @param small  8.dp
 * @param medium  16.dp
 * @param large  24.dp
 * @param extraLarge  32.dp
 */
val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

/**
 * function that returns [Shapes]
 * @return [Shapes] top avoid repetition of MaterialTheme.Shapes
 * @see shapes
 */

@Composable
fun shapes(): Shapes {
    return MaterialTheme.shapes
}
