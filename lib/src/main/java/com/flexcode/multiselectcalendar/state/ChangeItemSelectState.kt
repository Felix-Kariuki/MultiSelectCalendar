package com.flexcode.multiselectcalendar.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import com.flexcode.multiselectcalendar.utils.addOrRemoveIfExists
import java.time.LocalDate

/**
 * Allows for changing selection on runtime
 * @property onSelectChange Callback that returns false on selection change
 * @param selected a List of [LocalDate]
 *
 */

@OptIn(ExperimentalMultiSelectCalendarApi::class)
@Stable
public class ChangeItemSelectState(
    private val onSelectChange: (newValue: List<LocalDate>) -> Boolean = { true },
    selected: List<LocalDate>,
) : ItemSelectState {

    private var _selected by mutableStateOf(selected)

    public var selected: List<LocalDate>
        get() = _selected
        set(item) {
            if (item != selected && onSelectChange(item)) {
                _selected = item
            }
        }

    @ExperimentalMultiSelectCalendarApi
    override fun isDateSelected(date: LocalDate): Boolean {
        return selected.contains(date)
    }

    @ExperimentalMultiSelectCalendarApi
    override fun onDateSelected(date: LocalDate) {
        selected = ChangeSelected.getNewSelected(date, selected)
    }

    internal companion object {

        @ExperimentalMultiSelectCalendarApi
        fun saveSelected(
            onSelectChange: (newValue: List<LocalDate>) -> Boolean,
        ): Saver<ChangeItemSelectState, Any> = listSaver(
            save = { item ->
                if (item.selected.isNotEmpty()) {
                    listOf(item.selected.map { it.toString() })
                } else {
                    emptyList()
                }
            },
            restore = { restored ->
                ChangeItemSelectState(
                    onSelectChange = onSelectChange,
                    selected = (restored[1] as? List<String>)?.map {
                        LocalDate.parse(it)
                    }.orEmpty(),
                )
            },
        )
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
