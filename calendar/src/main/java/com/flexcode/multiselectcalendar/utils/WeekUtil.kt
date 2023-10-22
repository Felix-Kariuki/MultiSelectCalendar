package com.flexcode.multiselectcalendar.utils

import android.os.Build
import com.flexcode.multiselectcalendar.day.WeekDay
import com.flexcode.multiselectcalendar.day.WeekDays
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

internal const val DaysInAWeek = 7

@ExperimentalMultiSelectCalendarApi
internal fun YearMonth.getWeeks(firstDayOfTheWeek: DayOfWeek, today: LocalDate): List<WeekDays> {
    val daysLength = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        lengthOfMonth()
    } else {
        throw CustomException("Not Supported")
    }

    val starOffset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        atDay(1).dayOfWeek daysUntil firstDayOfTheWeek
    } else {
        throw CustomException("Not Supported")
    }
    val endOffset =
        DaysInAWeek - (atDay(daysLength).dayOfWeek daysUntil firstDayOfTheWeek) - 1

    return (1 - starOffset..daysLength + endOffset).chunked(DaysInAWeek).mapIndexed { index, days ->
        WeekDays(
            isFirstWeekOfTheMonth = index == 0,
            days = days.map { dayOfMonth ->
                val (date, isFromCurrentMonth) = when (dayOfMonth) {
                    in Int.MIN_VALUE..0 -> {
                        val previousMonth = this.minusMonths(1)
                        previousMonth.atDay(previousMonth.lengthOfMonth() + dayOfMonth) to false
                    }
                    in 1..daysLength -> atDay(dayOfMonth) to true
                    else -> {
                        val previousMonth = this.plusMonths(1)
                        previousMonth.atDay(dayOfMonth - daysLength) to false
                    }
                }

                WeekDay(
                    date = date,
                    isFromCurrentMonth = isFromCurrentMonth,
                    isCurrentDay = date.equals(today)
                )
            }
        )
    }
}
