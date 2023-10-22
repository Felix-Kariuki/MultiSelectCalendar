package com.flexcode.multiselectcalendar.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

public fun getDayName(date: Date): String {
    val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
    return dateFormat.format(date)
}
