package com.flexcode.multiselectcalendar.state.month

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.YearMonth

@ExperimentalMultiSelectCalendarApi
public fun monthState(initialMonth: YearMonth): MonthState = MonthStateImpl(initialMonth)

@ExperimentalMultiSelectCalendarApi
public interface MonthState {
    public var currentMonth: YearMonth

    public companion object {

        public fun saveMonthState(): Saver<MonthState, String> {
            return Saver(
                save = {
                    it.currentMonth.toString()
                },
                restore = {
                    /**
                     * YearMonth not supported for android versions below O
                     * todo fix
                     */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        monthState(YearMonth.parse(it))
                    } else {
                        throw CustomException("Not supported for this version")
                    }
                }
            )
        }
    }
}

@ExperimentalMultiSelectCalendarApi
private class MonthStateImpl(
    initialMonth: YearMonth
) : MonthState {
    private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

    override var currentMonth: YearMonth
        get() = _currentMonth
        set(value) {
            _currentMonth = value
        }
}
