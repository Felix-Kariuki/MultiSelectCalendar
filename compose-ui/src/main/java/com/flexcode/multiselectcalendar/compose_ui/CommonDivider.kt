package com.flexcode.multiselectcalendar.compose_ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flexcode.multiselectcalendar.compose_ui.theme.stroke

/**
 * shared divider item
 * @param modifier [Modifier]
 * @param padding this is the vertical padding
 * @param startPadding the horizontal padding
 * @param color the color of the divider
 * @param thickness thickness og the divider
 */
@Composable
fun CommonDivider(
    modifier: Modifier = Modifier,
    padding: Dp = spacing().none,
    startPadding: Dp = spacing().medium,
    color: Color = stroke,
    thickness: Dp = 1.dp
) {
    Divider(
        thickness = thickness,
        color = color,
        modifier = modifier.padding(vertical = padding, horizontal = startPadding)
    )
}
