package com.flexcode.multiselectcalendar.month

import android.os.Build
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flexcode.multiselectcalendar.day.WeekRow
import com.flexcode.multiselectcalendar.state.ItemSelectState
import com.flexcode.multiselectcalendar.state.day.DayState
import com.flexcode.multiselectcalendar.state.month.MonthState
import com.flexcode.multiselectcalendar.utils.CustomException
import com.flexcode.multiselectcalendar.utils.ExperimentalMultiSelectCalendarApi
import com.flexcode.multiselectcalendar.utils.getWeeks
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalSnapperApi::class)
@ExperimentalMultiSelectCalendarApi
@Composable
internal fun <T : ItemSelectState> ScrollableMonthItem(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    itemSelectState: T,
    monthState: MonthState,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
    container: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = StartIndex,
    )
    val flingBehavior = rememberSnapperFlingBehavior(
        lazyListState = listState,
        snapOffsetForItem = SnapOffsets.Start,
        springAnimationSpec = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        decayAnimationSpec = rememberSplineBasedDecay(),
        snapIndex = coerceSnapIndex,
    )

    val monthListState = remember {
        MonthListState(
            coroutineScope = coroutineScope,
            initialMonth = currentMonth,
            monthState = monthState,
            listState = listState,
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            content = { weekHeader(daysOfWeek) },
        )
    }
    LazyRow(
        modifier = modifier,
        state = listState,
        flingBehavior = flingBehavior,
        verticalAlignment = Alignment.Top,
    ) {
        items(PagerItemCount) { index ->
            MonthContent(
                modifier = Modifier.fillParentMaxWidth(),
                selectionState = itemSelectState,
                currentMonth = monthListState.getMonthForPage(index),
                today = today,
                daysOfWeek = daysOfWeek,
                dayContent = dayContent,
                monthContainer = container,
            )
        }
    }
}

@ExperimentalMultiSelectCalendarApi
@Composable
internal fun <T : ItemSelectState> MonthContent(
    selectionState: T,
    currentMonth: YearMonth,
    daysOfWeek: List<DayOfWeek>,
    today: LocalDate,
    modifier: Modifier = Modifier,
    dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
    monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
    Column {
        monthContainer { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues),
            ) {
                currentMonth.getWeeks(
                    firstDayOfTheWeek = daysOfWeek.first(),
                    today = today,
                ).forEach { week ->
                    WeekRow(
                        weekDays = week,
                        selectionState = selectionState,
                        dayContent = dayContent,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSnapperApi::class)
internal val coerceSnapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int =
    { _, startIndex, targetIndex ->
        targetIndex
            .coerceIn(startIndex - 1, startIndex + 1)
    }

@ExperimentalMultiSelectCalendarApi
@Stable
internal class MonthListState(
    private val coroutineScope: CoroutineScope,
    private val initialMonth: YearMonth,
    private val monthState: MonthState,
    private val listState: LazyListState,
) {

    private val currentFirstVisibleMonth by derivedStateOf {
        getMonthForPage(listState.firstVisibleItemIndex)
    }

    init {
        snapshotFlow { monthState.currentMonth }.onEach { month ->
            moveToMonth(month)
        }.launchIn(coroutineScope)

        with(listState) {
            snapshotFlow { currentFirstVisibleMonth }
                .throttleOnOffset()
                .onEach { newMonth ->
                    monthState.currentMonth = newMonth
                }.launchIn(coroutineScope)
        }
    }

    fun getMonthForPage(index: Int): YearMonth =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initialMonth.plusMonths((index - StartIndex).toLong())
        } else {
            throw CustomException("Not Supported")
        }

    private fun moveToMonth(month: YearMonth) {
        if (month == currentFirstVisibleMonth) return
        initialMonth.minus(month).let { offset ->
            coroutineScope.launch {
                listState.animateScrollToItem((StartIndex - offset).toInt())
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MonthListState

        if (monthState != other.monthState) return false
        if (listState != other.listState) return false

        return true
    }

    override fun hashCode(): Int {
        var result = monthState.hashCode()
        result = 31 * result + listState.hashCode()
        return result
    }
}

private operator fun YearMonth.minus(other: YearMonth) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ChronoUnit.MONTHS.between(other, this)
    } else {
        throw CustomException("Not Supported")
    }

internal const val PagerItemCount = 20_000
internal const val StartIndex = PagerItemCount / 2

context(LazyListState) internal fun <T> Flow<T>.throttleOnOffset() = combine(
    snapshotFlow { firstVisibleItemScrollOffset },
) { newMonth, offset ->
    newMonth to (offset <= MinimalOffsetForEmit)
}.filter { (_, shouldUpdate) ->
    shouldUpdate
}.map { (newValue, _) -> newValue }

private const val MinimalOffsetForEmit = 10
