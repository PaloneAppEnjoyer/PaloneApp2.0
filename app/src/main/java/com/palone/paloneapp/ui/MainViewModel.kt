package com.palone.paloneapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palone.paloneapp.data.models.HomeScreenUiState
import com.palone.paloneapp.domain.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.*

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _currentCalendar = MutableStateFlow(Calendar.getInstance())


    fun updateSelectedLocalDate(date: LocalDate) {
        _uiState.update { it.copy(selectedLocalDate = date) }
    }

    suspend fun openDrawer() {
        _uiState.value.scaffoldState.drawerState.open()
    }

    suspend fun closeDrawer() {
        _uiState.value.scaffoldState.drawerState.close()
    }

    fun updateTextFilter(query: String) {
        _uiState.update { it.copy(classFilter = query) }
    }

    fun showTextFilterDialog() {
        _uiState.update { it.copy(shouldShowFilterDialog = true) }
    }

    fun hideTextFilterDialog() {
        _uiState.update { it.copy(shouldShowFilterDialog = false) }
    }

    fun refreshSubstitutionsDataWithLocalDate(localDate: LocalDate, onFinish: () -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    substitutionsList = UseCases().getSubstitutionsDataWithLocalDate(localDate),
                )
            }
            onFinish()
        }
    }

    fun refreshFilteredSubstitutionsWithQuery(query: String = _uiState.value.classFilter) {
        _uiState.update { it.copy(isLoading = true) }
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                filteredSubstitutionsList = _uiState.value.substitutionsList?.let {
                    UseCases().getFilteredSubstitutionDataByQuery(
                        it, query
                    )
                }
            )
        }
    }


    init {
        updateSelectedLocalDate(
            LocalDate(
                year = _currentCalendar.value.get(Calendar.YEAR),
                monthNumber = _currentCalendar.value.get(Calendar.MONTH) + 1,
                dayOfMonth = _currentCalendar.value.get(Calendar.DAY_OF_MONTH)
            )
        )
        refreshSubstitutionsDataWithLocalDate(
            _uiState.value.selectedLocalDate
        ) {
            refreshFilteredSubstitutionsWithQuery()
        }

    }
}
