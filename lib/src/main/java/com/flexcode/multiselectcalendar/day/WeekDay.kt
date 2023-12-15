package com.flexcode.multiselectcalendar.day

import androidx.compose.runtime.Immutable
import com.flexcode.multiselectcalendar.state.day.DayItem
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.LocalDate

@ExperimentalMultiSelectCalendarApi
@Immutable
internal class WeekDay(
    override val date: LocalDate,
    override val isCurrentDay: Boolean,
    override val isFromCurrentMonth: Boolean,
) : DayItem

@Immutable
internal data class WeekDays(
    val isFirstWeekOfTheMonth: Boolean = false,
    val days: List<DayItem>,
)
