package com.palone.paloneapp.feature_screen_timetable.data.models

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState

data class TimetableScreenUiState(
    val selectedSchoolClass: String = "4ftg",
    val hiddenGroups: List<String> = emptyList(),
    val selectedDay: String = "Pn",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val lessonsList: List<TimetableLessons> = emptyList(),
    val scaffoldState: ScaffoldState = ScaffoldState(
        DrawerState(DrawerValue.Closed),
        SnackbarHostState()
    ),
    val currentLesson: Float = -1.0f
)
