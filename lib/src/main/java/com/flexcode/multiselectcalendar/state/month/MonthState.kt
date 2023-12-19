package com.flexcode.multiselectcalendar.state.month

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.YearMonth

@ExperimentalMultiSelectCalendarApi
public fun monthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@ExperimentalMultiSelectCalendarApi
@Stable
public interface MonthState {
    public var currentMonth: YearMonth

    public companion object {

        public fun saveMonthState(): Saver<MonthState, String> {
            return Saver(
                save = {
                    it.currentMonth.toString()
                },
                restore = {
                    monthState(YearMonth.parse(it))
                },
            )
        }
    }
}

@ExperimentalMultiSelectCalendarApi
@Stable
private class MonthStateImpl(
    initialMonth: YearMonth,
) : MonthState {
    private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

    override var currentMonth: YearMonth
        get() = _currentMonth
        set(value) {
            _currentMonth = value
        }
}
