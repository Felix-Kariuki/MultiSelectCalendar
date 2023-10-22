package com.flexcode.multiselectcalendar.utils

import android.os.Build
import java.time.DayOfWeek
import java.time.LocalDate

internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) = if (contains(date)) {
    this - date
} else {
    this + date
}

internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        (7 + (value - other.value)) % 7
    } else {
        throw CustomException()
    }
