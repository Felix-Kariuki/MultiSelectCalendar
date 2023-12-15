package com.flexcode.multiselectcalendar.calendar

import com.flexcode.multiselectcalendar.extensions.getDayName
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

public class ExtensionsTest {

    @Test
    public fun `test get day name gets correct day`() {
        val currentDay = Date(123, 9, 21, 18, 6, 51) // "Sat Oct 21 18:06:51 UTC 2023"
        val expectedDay = "Sat"
        val actualDay = getDayName(currentDay)
        assertEquals(actualDay, expectedDay)
    }
}
