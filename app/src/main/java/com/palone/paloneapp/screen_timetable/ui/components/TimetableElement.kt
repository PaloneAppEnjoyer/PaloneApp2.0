package com.palone.paloneapp.screen_timetable.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.palone.paloneapp.screen_substitutions.data.models.SubstitutionDataEntry
import com.palone.paloneapp.screen_timetable.data.models.BellPeriod
import com.palone.paloneapp.screen_timetable.data.models.TimetableDataEntry

@Composable
fun TimetableElement(
    data: List<TimetableDataEntry>,
    modifier: Modifier = Modifier,
    lessonNumber: Int,
    showSchoolClass: Boolean = false,
    currentLesson: Float,
    todayDayInWeek: Int,
    substitutions: List<SubstitutionDataEntry> = emptyList(),
    areSubstitutionsForTomorrow: Boolean = false
) {
    if (data.isNotEmpty()) {
        val isNow =
            data[0].dayInt == todayDayInWeek - 1 && lessonNumber.toFloat() - currentLesson == 0.0f
        val isRightAfter =
            data[0].dayInt == todayDayInWeek - 1 && lessonNumber.toFloat() - currentLesson == -0.5f
        val isRightBefore =
            data[0].dayInt == todayDayInWeek - 1 && lessonNumber.toFloat() - currentLesson == 0.5f
        val shouldShowSubstitutions = remember { mutableStateOf(false) }

        val bellsPeriods = listOf(
            BellPeriod(0, "07:05", "07:50"),
            BellPeriod(1, "07:55", "08:40"),
            BellPeriod(2, "08:45", "09:30"),
            BellPeriod(3, "09:35", "10:20"),
            BellPeriod(4, "10:25", "11:10"),
            BellPeriod(5, "11:25", "12:10"),
            BellPeriod(6, "12:15", "13:00"),
            BellPeriod(7, "13:05", "13:50"),
            BellPeriod(8, "13:55", "14:40"),
            BellPeriod(9, "14:45", "15:30"),
            BellPeriod(10, "15:35", "16:20")
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable(enabled = substitutions.isNotEmpty()) {
                    shouldShowSubstitutions.value = !shouldShowSubstitutions.value
                }
                .animateContentSize(),
            backgroundColor = MaterialTheme.colors.primaryVariant, border = BorderStroke(
                3.dp, when {
                    isNow -> Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.secondary
                        )
                    )
                    isRightBefore -> Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.primaryVariant,
                        )
                    )
                    isRightAfter -> Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primaryVariant,
                            MaterialTheme.colors.secondary,
                        )
                    )
                    else -> Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primaryVariant,
                            MaterialTheme.colors.primaryVariant
                        )
                    )

                }
            )
        ) {
            Row(modifier = Modifier.padding(15.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(30),
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(5.dp),

                            ) {
                            Text(
                                text = lessonNumber.toString(),
                                fontSize = 30.sp, modifier = Modifier.padding(end = 3.dp)
                            )
                            Column {
                                Text(
                                    text = bellsPeriods.find { it.id == lessonNumber }?.starttime
                                        ?: "00:00",
                                    fontSize = 10.sp,
                                )
                                Text(
                                    text = bellsPeriods.find { it.id == lessonNumber }?.endtime
                                        ?: "00:00",
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    }
                    if (substitutions.isNotEmpty())
                        Crossfade(shouldShowSubstitutions.value) {
                            if (!it) Icon(Icons.Default.KeyboardArrowDown, null)
                            else Icon(Icons.Default.KeyboardArrowUp, null)
                        }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                    modifier = Modifier.padding(start = 15.dp)
                ) {
                    data.sortedBy { it.groupName }.forEach {
                        TimetableElementContent(
                            isNow = isNow,
                            data = it,
                            showSchoolClass = showSchoolClass, vertical = false
                        )
                    }
                    if (shouldShowSubstitutions.value) {
                        Text(text = if (areSubstitutionsForTomorrow) "Jutro:" else "Dzisiaj:")
                        substitutions.sortedBy { it.subject }.forEach {
                            TimetableElementContent(
                                isNow = false,
                                data = TimetableDataEntry(
                                    subjectShortName = it.subject,
                                    subjectName = it.subject,
                                    classroomsName = it.roomChange,
                                    teacherShortName = it.teacherReplacement,
                                    schoolClassNames = emptyList(),
                                    duration = 0,
                                    dayName = "",
                                    lessonFrom = 0,
                                    lessonTo = 0,
                                    groupName = "",
                                    dayInt = 0
                                ),
                                showSchoolClass = false,
                                vertical = true
                            )
                        }
                    }


                }
            }
        }
    }

}

@Composable
fun TimetableElementContent(
    isNow: Boolean,
    data: TimetableDataEntry,
    showSchoolClass: Boolean,
    vertical: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(
                LocalConfiguration.current.screenWidthDp.dp - 140.dp
            )
        ) {
            if (isNow)
                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Text(
                        text = data.subjectName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                }
            else
                Text(
                    text = data.subjectName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary
                )

        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            if (vertical)
                Column {
                    Text(
                        text = data.teacherShortName,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                    Text(text = data.classroomsName, fontSize = 17.sp)
                }
            else {
                Text(
                    text = data.teacherShortName,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                Text(text = data.groupName, color = MaterialTheme.colors.primary)
                Text(text = data.classroomsName, fontSize = 17.sp)
            }

            if (showSchoolClass) data.schoolClassNames.forEach { Text(text = it) }
        }
    }
}
