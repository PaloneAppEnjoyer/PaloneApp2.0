package com.palone.paloneapp.ui

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.data.models.TimetableData
import com.palone.paloneapp.data.models.TimetableLessons
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

    private var timetableList: List<TimetableData> = emptyList()
    private var allSchoolClassNames: MutableList<String> = mutableListOf()
    override fun onFabClick(navHostController: NavHostController) {
        navHostController.navigate(ScreensProperties.SubstitutionsScreen.route)
    }

    override suspend fun openDrawer() {
        _uiState.value.scaffoldState.drawerState.open()
    }

    override suspend fun closeDrawer() {
        _uiState.value.scaffoldState.drawerState.close()
    }

    fun setThisGroupHidden(group: String) {
        val currentHiddenGroups = _uiState.value.hiddenGroups.toMutableList()
        currentHiddenGroups.add(group)
        _uiState.update {
            it.copy(
                hiddenGroups = currentHiddenGroups,
                lessonsList = getTimetableLessons()
            )
        }
    }

    fun unsetThisGroupHidden(group: String) {
        val currentHiddenGroups = _uiState.value.hiddenGroups.toMutableList()
        currentHiddenGroups.remove(group)
        _uiState.update {
            it.copy(
                hiddenGroups = currentHiddenGroups,
                lessonsList = getTimetableLessons()
            )
        }
    }

    fun getAllSchoolClassesNames(): List<String> {
        return allSchoolClassNames.toList()
    }

    fun getRawTimetableList(): List<TimetableData> {
        return timetableList
    }

    fun setSchoolClassQuery(schoolClassName: String) {
        _uiState.update { it.copy(selectedSchoolClass = schoolClassName, isLoading = true) }
        _uiState.update { it.copy(lessonsList = getTimetableLessons(), isLoading = false) }
    }

    private fun getTimetableLessons(): List<TimetableLessons> {
        timetableList.sortedBy { it.className }
            .filter { it.className == _uiState.value.selectedSchoolClass }.forEach {
                it.day.filter { it2 -> it2.dayNameShorted == _uiState.value.selectedDay }
                    .forEach { it2 ->
                        return it2.lessons
                    }
            }
        return emptyList()
    }

    private fun refreshTimetableWithNewData(data: List<TimetableData>) {
        _uiState.update { it.copy(isLoading = true) }
        timetableList = data
        timetableList.forEach { allSchoolClassNames.add(it.className) }
        _uiState.update { it.copy(lessonsList = getTimetableLessons(), isLoading = false) }
    }

    fun selectDay(day: String) {
        _uiState.update { it.copy(selectedDay = day, isLoading = true) }
        _uiState.update { it.copy(lessonsList = getTimetableLessons(), isLoading = false) }
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