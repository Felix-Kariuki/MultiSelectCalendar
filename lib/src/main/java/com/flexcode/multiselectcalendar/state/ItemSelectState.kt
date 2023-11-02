package com.flexcode.multiselectcalendar.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.LocalDate

/**
 * Item Selection state interface
 */
@ExperimentalMultiSelectCalendarApi
@Stable
public interface ItemSelectState {
    public fun isDateSelected(date: LocalDate): Boolean = false
    public fun onDateSelected(date: LocalDate) {}
}

@ExperimentalMultiSelectCalendarApi
@Immutable
public object NoItemSelectedState : ItemSelectState {
    override fun isDateSelected(date: LocalDate): Boolean = false

    override fun onDateSelected(date: LocalDate): Unit = Unit
}
