package com.palone.paloneapp.timetable.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.timetable.data.models.TimetableDataEntry
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DisplayTeacherTimetableDialog(data: List<TimetableData>, onDismissRequest: () -> Unit = {}) {
    val calendarTodayDayOfWeek = remember {
        mutableStateOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
    }
    val query = remember { mutableStateOf("") }
    val selectedDay = remember {
        mutableStateOf("Pn")
    }
    val dataToDisplay: MutableList<Pair<Int, MutableList<TimetableDataEntry>>> = mutableListOf()
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            DaySelector(
                selectedDay = selectedDay.value,
                onDaySelected = { selectedDay.value = it;dataToDisplay.clear() },
                isHorizontal = true
            )
            TextField(
                value = query.value,
                onValueChange = { query.value = it;dataToDisplay.clear() })
            if (query.value.length > 1)
                data.forEach { it1 ->
                    it1.day.filter { it.dayNameShorted == selectedDay.value }.forEach { it2 ->
                        it2.lessons.forEach { it3 ->
                            it3.entries.forEach { it4 ->
                                if (it4.teacherShortName.contains(query.value)) dataToDisplay.add(
                                    Pair(
                                        it3.lessonNumber,
                                        listOf(it4).toMutableList()
                                    )
                                )
                            }
                        }
                    }
                }
            else dataToDisplay.clear()
            for (i in 0..10) {
                dataToDisplay.filter { it.first == i }.forEach {
                    TimetableElement(
                        data = it.second,
                        lessonNumber = i,
                        showSchoolClass = true,
                        currentLesson = -1.0f,
                        todayDayInWeek = calendarTodayDayOfWeek.value
                    )
                }
            }

        }
    }
}
