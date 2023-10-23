package com.flexcode.multiselectcalendar.month

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.flexcode.multiselectcalendar.state.month.MonthState
import com.flexcode.multiselectcalendar.utils.CalendarCommonDivider
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi

/**
 * Custom month header item
 * @param monthState [MonthState]
 * @param modifier [Modifier]
 * @param showYear Boolean to control if year to be shown or show only the month
 */
@ExperimentalMultiSelectCalendarApi
@Composable
public fun MonthHeaderItem(
    monthState: MonthState,
    modifier: Modifier = Modifier,
    showYear: Boolean = true
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = monthState.currentMonth.month
                            .name
                            .lowercase()
                            .replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    throw CustomException("not supported currently")
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && showYear) {
                    Text(
                        text = monthState.currentMonth.year.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF434343)
                    )
                } else {
                    throw CustomException("not supported currently")
                }
            }

            Row {
                IconButton(

                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            monthState.currentMonth =
                                monthState.currentMonth.minusMonths(1)
                        } else {
                            throw CustomException("not supported currently")
                        }
                    }
                ) {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        colorFilter = ColorFilter.tint(Color(0xFF7E818C)),
                        contentDescription = "Previous"
                    )
                }

                IconButton(
                    onClick = {
                        monthState.currentMonth = monthState.currentMonth.plusMonths(1)
                    }
                ) {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        colorFilter = ColorFilter.tint(Color(0xFF7E818C)),
                        contentDescription = "Next"
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(8.dp))

        CalendarCommonDivider(thickness = 1.5.dp)

        Spacer(modifier = modifier.height(8.dp))
    }
}
