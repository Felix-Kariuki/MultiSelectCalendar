package com.flexcode.multiselectcalendar.dayWeek

import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.flexcode.multiselectcalendar.compose_ui.spacing
import com.flexcode.multiselectcalendar.compose_ui.theme.colorGray
import com.flexcode.multiselectcalendar.utils.CustomException
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

/**
 * this is the days of the week header item Ui can be customized to suit the a specific use case
 * format as need be
 * @param daysOfWeek a list of [DayOfWeek] items
 * @param modifier [Modifier] for additional styling
 */

@Composable
public fun DayOfTheWeekHeaderItem(daysOfWeek: List<DayOfWeek>, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(top = spacing().small)) {
        daysOfWeek.forEach { dayOfWeek ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    color = colorGray,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                throw CustomException()
            }
        }
    }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
