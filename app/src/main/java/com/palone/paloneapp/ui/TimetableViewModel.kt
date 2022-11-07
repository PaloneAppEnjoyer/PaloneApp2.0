package com.palone.paloneapp.ui

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.TimetableScreenUiState
import com.palone.paloneapp.domain.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimetableViewModel : MainViewModel() {
    private val _uiState = MutableStateFlow(TimetableScreenUiState())
    val uiState: StateFlow<TimetableScreenUiState> = _uiState.asStateFlow()
    override fun onFabClick(navHostController: NavHostController) {
        navHostController.navigate(ScreensProperties.SubstitutionsScreen.route)
    }

    override suspend fun openDrawer() {
        _uiState.value.scaffoldState.drawerState.open()
    }

    override suspend fun closeDrawer() {
        _uiState.value.scaffoldState.drawerState.close()
    }


    private fun refreshTimetableWithNewData(data: List<TimetableData>) {
        _uiState.update { it.copy(isLoading = true) }
        _uiState.update { it.copy(timetableList = data, isLoading = false) }
    }

    init {
        viewModelScope.launch {
            val timetableData = UseCases().getTimetableData(
                UseCases().getTtViewerData().response?.regular?.default_num?.toInt() ?: 100
            )
            UseCases().saveTimetableDataToLocalJsonFile(
                timetableData = timetableData,
                filePath = directory,
                "latest_timetable_data.json"
            ) {
                refreshTimetableWithNewData(
                    UseCases().getTimetableDataFromLocalJsonFile(
                        directory,
                        "latest_timetable_data.json"
                    )
                )
            }
        }

    }
}