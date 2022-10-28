package com.palone.paloneapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _cal = MutableStateFlow(Calendar.getInstance())
    val cal: StateFlow<Calendar> = _cal.asStateFlow()

    private val _selectedLocalDate = MutableStateFlow(
        LocalDate(
            year = _cal.value.get(Calendar.YEAR),
            monthNumber = _cal.value.get(Calendar.MONTH) + 1,
            dayOfMonth = _cal.value.get(Calendar.DAY_OF_MONTH)
        )
    )
    val selectedLocalDate: StateFlow<LocalDate> = _selectedLocalDate.asStateFlow()

    fun updateSelectedLocalDate(date: LocalDate) {
        _selectedLocalDate.value = date
    }

    fun refreshSubstitutionsDataWithLocalDate(localDate: LocalDate) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    substitutionsList = UseCases().getSubstitutionsDataWithLocalDate(localDate)
                )
            }
        }
    }

    init {
        refreshSubstitutionsDataWithLocalDate(
            _selectedLocalDate.value
        )
    }
}
