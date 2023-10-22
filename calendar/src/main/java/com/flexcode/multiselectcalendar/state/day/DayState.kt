package com.flexcode.multiselectcalendar.state.day

import com.flexcode.multiselectcalendar.state.ItemSelectState
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi

/**
 * Day State of the selected day
 * @property day item of [DayItem] current selected day item
 * @property itemSelectState selection State [ItemSelectState]
 */
@ExperimentalMultiSelectCalendarApi
public class DayState<T : ItemSelectState>(
    private val day: DayItem,
    public val itemSelectState: ItemSelectState
) : DayItem by day
