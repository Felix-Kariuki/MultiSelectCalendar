package com.flexcode.multiselectcalendar.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * shared divider item
 * @param modifier [Modifier]
 * @param padding this is the vertical padding
 * @param startPadding the horizontal padding
 * @param color the color of the divider
 * @param thickness thickness og the divider
 */
@Composable
public fun CalendarCommonDivider(
    modifier: Modifier = Modifier,
    padding: Dp = 0.dp,
    startPadding: Dp = 16.dp,
    color: Color = Color(0xFFEFEFEF),
    thickness: Dp = 1.dp
) {
    Divider(
        thickness = thickness,
        color = color,
        modifier = modifier.padding(vertical = padding, horizontal = startPadding)
    )
}
