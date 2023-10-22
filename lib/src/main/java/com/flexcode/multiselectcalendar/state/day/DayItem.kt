package com.flexcode.multiselectcalendar.state.day

import java.time.LocalDate

/**
 * Interface item for the day shown on the calendar
 *
 * @property date local date of the day
 * @property isCurrentDay whenever the day is the today's date
 * @property isFromCurrentMonth whenever the day is from currently rendered month
 */
public interface DayItem {
    public val date: LocalDate
    public val isCurrentDay: Boolean
    public val isFromCurrentMonth: Boolean
}
