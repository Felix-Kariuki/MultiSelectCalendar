package com.flexcode.multiselectcalendar.state

import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.LocalDate

/**
 * Item Selection state interface
 */
@ExperimentalMultiSelectCalendarApi
public interface ItemSelectState {
    public fun isDateSelected(date: LocalDate): Boolean = false
    public fun onDateSelected(date: LocalDate) {}
}

@ExperimentalMultiSelectCalendarApi
public object NoItemSelectedState : ItemSelectState {
    override fun isDateSelected(date: LocalDate): Boolean = false

    override fun onDateSelected(date: LocalDate): Unit = Unit
}
