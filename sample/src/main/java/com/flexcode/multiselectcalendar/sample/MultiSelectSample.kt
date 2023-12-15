package com.flexcode.multiselectcalendar.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.flexcode.multiselectcalendar.MultiSelectCalendar
import com.flexcode.multiselectcalendar.compose_ui.spacing
import com.flexcode.multiselectcalendar.compose_ui.theme.MultiSelectCalendarTheme
import com.flexcode.multiselectcalendar.rememberMultiSelectCalendarState
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi

@OptIn(ExperimentalMultiSelectCalendarApi::class)
@Composable
fun MultiSelectSample(modifier: Modifier = Modifier) {
    val multiSelectState = rememberMultiSelectCalendarState()

    Column(
        modifier
            .fillMaxSize()
            .padding(horizontal = spacing().medium, vertical = spacing().extraLarge),
    ) {
        MultiSelectCalendar(
            modifier = modifier,
            calendarState = multiSelectState,
        )

        Spacer(modifier = modifier.height(spacing().large))

        val dates = multiSelectState.selectedState.selected
        if (dates.isNotEmpty()) Text(text = "$dates", fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSelectSamplePreview() {
    MultiSelectCalendarTheme {
        MultiSelectSample()
    }
}
