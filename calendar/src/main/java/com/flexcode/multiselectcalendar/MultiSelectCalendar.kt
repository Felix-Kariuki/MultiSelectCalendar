package com.flexcode.multiselectcalendar

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.flexcode.multiselectcalendar.day.DayItem
import com.flexcode.multiselectcalendar.dayWeek.DayOfTheWeekHeaderItem
import com.flexcode.multiselectcalendar.dayWeek.rotateRight
import com.flexcode.multiselectcalendar.month.MonthHeaderItem
import com.flexcode.multiselectcalendar.month.ScrollableMonthItem
import com.flexcode.multiselectcalendar.state.ChangeItemSelectState
import com.flexcode.multiselectcalendar.state.ItemSelectState
import com.flexcode.multiselectcalendar.state.day.DayState
import com.flexcode.multiselectcalendar.state.month.MonthState
import com.flexcode.multiselectcalendar.state.month.monthState
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.DaysInAWeek
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * @param modifier [Modifier]
 * @param content the content container for all the days in the month
 * @param calendarState state
 * @param daysOfWeekItem item showing the days of the week
 * @param dayItem item show the day item
 * @param monthHeaderItem header for showing current month
 * @param shouldShowYear Boolean controlling if moth header should contain the current year
 * @param showCurrentDay Boolean to control if current day is visible / has a border to highlight
 */
@ExperimentalMultiSelectCalendarApi
@Composable
public fun MultiSelectCalendar(
    modifier: Modifier,
    shouldShowYear: Boolean = true,
    showCurrentDay: Boolean = false,
    calendarState: CustomCalendarState<ChangeItemSelectState> = rememberMultiSelectCalendarState(),
    daysOfWeekItem: @Composable BoxScope.(List<DayOfWeek>) -> Unit = {
        DayOfTheWeekHeaderItem(it)
    },
    dayItem: @Composable BoxScope.(DayState<ChangeItemSelectState>) -> Unit = {
        DayItem(state = it, showCurrentDay = showCurrentDay)
    },
    monthHeaderItem: @Composable ColumnScope.(MonthState) -> Unit = {
        MonthHeaderItem(
            it,
            showYear = shouldShowYear
        )
    },
    content: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { contents ->
        Box { contents(PaddingValues()) }
    }
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CustomCalendar(
            modifier = modifier,
            customCalendarState = calendarState,
            currentDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek,
            today = LocalDate.now(),
            daysOfWeekItem = daysOfWeekItem,
            monthHeaderItem = monthHeaderItem,
            dayItem = dayItem,
            content = content
        )
    } else {
        throw CustomException()
    }
}

/**
 * the state of the Multi select calendar [Composable]
 * @property monthState the moth currently showing on the calendar
 * @property selectedState state that handles calendar selection
 */
@ExperimentalMultiSelectCalendarApi
public class CustomCalendarState<T : ItemSelectState>(
    public val monthState: MonthState,
    public val selectedState: T
)

/**
 * create and [remember] the [MultiSelectCalendar] providing selection state
 *
 * @param initialSelectedDates List of the initial dates that are to be marked as the selected
 * other selected dates should be appended to this list
 * @return [CustomCalendarState]
 * @param onSelectChange callback for on item select change
 * @param selectedState [ChangeItemSelectState] for toggling selected items on runtime
 * @param monthState [MonthState]
 */

@ExperimentalMultiSelectCalendarApi
@Composable
public fun rememberMultiSelectCalendarState(
    initialSelectedDates: List<LocalDate> = emptyList(),
    onSelectChange: (newValue: List<LocalDate>) -> Boolean = { true },
    monthState: MonthState = rememberSaveable(saver = MonthState.saveMonthState()) {
        val initialMonth: YearMonth
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initialMonth = YearMonth.now()
        } else {
            throw CustomException("Not supported for this version of android")
        }
        monthState(initialMonth = initialMonth)
    },
    selectedState: ChangeItemSelectState = rememberSaveable(
        saver = ChangeItemSelectState.saveSelected(onSelectChange)
    ) {
        ChangeItemSelectState(onSelectChange, initialSelectedDates)
    }
): CustomCalendarState<ChangeItemSelectState> = remember {
    CustomCalendarState(monthState = monthState, selectedState = selectedState)
}

/**
 * The calendar Composable
 *
 * @param modifier [Modifier]
 * @param customCalendarState state of the customCalendar
 * @param currentDayOfWeek the current day of the week
 * @param daysOfWeekItem item showing the days of the week
 * @param dayItem item show the day item
 * @param monthHeaderItem header for showing current month
 * @param content the content container for all the days in the month
 *
 */
@ExperimentalMultiSelectCalendarApi
@Composable
public fun <T : ItemSelectState> CustomCalendar(
    modifier: Modifier = Modifier,
    customCalendarState: CustomCalendarState<T>,
    currentDayOfWeek: DayOfWeek,
    today: LocalDate,
    shouldShowYear: Boolean = true,
    daysOfWeekItem: @Composable BoxScope.(List<DayOfWeek>) -> Unit = {
        DayOfTheWeekHeaderItem(it)
    },
    dayItem: @Composable BoxScope.(DayState<T>) -> Unit = {
        DayItem(state = it)
    },
    monthHeaderItem: @Composable ColumnScope.(MonthState) -> Unit = {
        MonthHeaderItem(
            it,
            showYear = shouldShowYear
        )
    },
    content: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit = { contents ->
        Box { contents(PaddingValues()) }
    }
) {
    val currentMonth = remember { customCalendarState.monthState.currentMonth }
    val daysOfWeek = remember(currentDayOfWeek) {
        DayOfWeek.values().rotateRight(DaysInAWeek - currentDayOfWeek.ordinal)
    }

    Column(
        modifier = modifier
    ) {
        monthHeaderItem(customCalendarState.monthState)
        ScrollableMonthItem(
            currentMonth = currentMonth,
            itemSelectState = customCalendarState.selectedState,
            monthState = customCalendarState.monthState,
            daysOfWeek = daysOfWeek,
            today = today,
            dayContent = dayItem,
            weekHeader = daysOfWeekItem,
            container = content
        )
    }
}
