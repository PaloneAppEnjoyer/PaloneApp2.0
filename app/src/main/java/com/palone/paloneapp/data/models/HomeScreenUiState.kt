package com.palone.paloneapp.data.models

data class HomeScreenUiState(
    val dayOffset: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val classFilter: String? = null,
    val substitutionsList: List<SubstitutionData>? = null
)
