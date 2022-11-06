package com.palone.paloneapp.data.models

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import kotlinx.datetime.LocalDate

data class SubstitutionsScreenUiState(
    var selectedLocalDate: LocalDate = LocalDate(2000, 1, 1),
    var shouldShowFilterDialog: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    var classFilter: String = "",
    val scaffoldState: ScaffoldState = ScaffoldState(
        DrawerState(DrawerValue.Closed),
        SnackbarHostState()
    ),
    val substitutionsList: List<SubstitutionData>? = null,
    val filteredSubstitutionsList: List<SubstitutionData>? = null,
)
