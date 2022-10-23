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

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun refreshSubstitutionsWithDayOffset(day: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    substitutionsList = UseCases().getSubstitutionsDataWithDayOffset(day)
                )
            }
        }
    }

    init {
        refreshSubstitutionsWithDayOffset(1)
    }
}
