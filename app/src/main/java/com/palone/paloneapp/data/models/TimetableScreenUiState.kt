package com.palone.paloneapp.data.models

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState

data class TimetableScreenUiState(
    val selectedSchoolClass: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val timetableList: List<TimetableData>? = null,
    val scaffoldState: ScaffoldState = ScaffoldState(
        DrawerState(DrawerValue.Closed),
        SnackbarHostState()
    ),
    val currentLesson: Float = -1.0f
)
