package com.flexcode.multiselectcalendar.dayWeek

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

/**
 * this is the days of the week header item Ui can be customized to suit the a specific use case
 * format as need be
 * @param daysOfWeek a list of [DayOfWeek] items
 * @param modifier [Modifier] for additional styling
 */

@Composable
public fun DayOfTheWeekHeaderItem(daysOfWeek: List<DayOfWeek>, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(top = 8.dp)) {
        daysOfWeek.forEach { dayOfWeek ->

            Text(
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight(),
                color = Color(0xFF7E818C),
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
