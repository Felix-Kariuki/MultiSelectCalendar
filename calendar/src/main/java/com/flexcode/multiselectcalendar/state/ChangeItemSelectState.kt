package com.flexcode.multiselectcalendar.state

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import com.flexcode.multiselectcalendar.utils.addOrRemoveIfExists
import java.time.LocalDate

/**
 * Allows for changing selection on runtime
 * @property onSelectChange Callback that returns false on selection change
 * @param selected a List of [LocalDate]
 *
 */

public class ChangeItemSelectState(
    private val onSelectChange: (newValue: List<LocalDate>) -> Boolean = { true },
    selected: List<LocalDate>
) : ItemSelectState {

    private var _selected by mutableStateOf(selected)

    public var selected: List<LocalDate>
        get() = _selected
        set(item) {
            if (item != selected && onSelectChange(item)) {
                _selected = item
            }
        }

    override fun isDateSelected(date: LocalDate): Boolean {
        return selected.contains(date)
    }

    override fun onDateSelected(date: LocalDate) {
        selected = ChangeSelected.getNewSelected(date, selected)
    }

    internal companion object {

        @ExperimentalMultiSelectCalendarApi
        fun saveSelected(
            onSelectChange: (newValue: List<LocalDate>) -> Boolean
        ): Saver<ChangeItemSelectState, Any> {
            return listSaver(
                save = { item ->
                    listOf(item.selected.map { it.toString() })
                },
                restore = { restored ->
                    ChangeItemSelectState(
                        onSelectChange = onSelectChange,
                        selected = (restored[1] as? List<String>)?.map {
                            /**
                             * todo add support for android versions below Android O
                             * use Date instead of LocalDate
                             */

                            /**
                             * todo add support for android versions below Android O
                             * use Date instead of LocalDate
                             */

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                LocalDate.parse(it)
                            } else {
                                throw CustomException("Not supported for this version")
                            }
                        }.orEmpty()
                    )
                }
            )
        }
    }
}

/**
 * Class to change selected and get the newly selected
 *
 */

public object ChangeSelected {

    /**
     * Allows to get newly selected
     * @return Lit [LocalDate]
     * @param date [LocalDate] the date on change select
     */
    public fun getNewSelected(date: LocalDate, selected: List<LocalDate>): List<LocalDate> {
        return selected.addOrRemoveIfExists(date)
    }
}
