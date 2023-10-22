package com.flexcode.multiselectcalendar.day

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexcode.multiselectcalendar.compose_ui.spacing
import com.flexcode.multiselectcalendar.compose_ui.theme.candyApple
import com.flexcode.multiselectcalendar.compose_ui.theme.textGray
import com.flexcode.multiselectcalendar.state.ItemSelectState
import com.flexcode.multiselectcalendar.state.day.DayState
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.LocalDate

/**
 * Custom day item
 * @param state the [DayState] of the current selected day item
 * @param modifier [Modifier]
 * @param selectedColor color of the selected item
 * @param onClick callback function for on day item click
 * @param showCurrentDay Boolean to control if current day is visible / has a border to highlight
 */
@OptIn(ExperimentalMultiSelectCalendarApi::class)
@Composable
public fun <T : ItemSelectState> DayItem(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    showCurrentDay: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.background,
    currentDayColor: Color = candyApple,
    onClick: (LocalDate) -> Unit = {}
) {
    val date = state.date
    val selectedState = state.itemSelectState

    val isSelected = selectedState.isDateSelected(date)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(spacing().small),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        border = if (state.isCurrentDay && showCurrentDay) {
            BorderStroke(
                1.dp,
                currentDayColor
            )
        } else {
            null
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) candyApple else selectedColor
        ),
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onClick(date)
                    selectedState.onDateSelected(date)
                }
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = date.dayOfMonth.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    color = if (isSelected) selectedColor else textGray
                )
            } else {
                throw CustomException("Not Supported for this version")
            }
        }
    }
}
