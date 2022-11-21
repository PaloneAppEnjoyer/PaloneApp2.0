package com.palone.paloneapp.ui

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.domain.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserImpl
import com.palone.paloneapp.feature_screen_substitutions.data.ScreensProperties
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableData
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableLessons
import com.palone.paloneapp.feature_screen_timetable.data.models.TimetableScreenUiState
import com.palone.paloneapp.feature_screen_timetable.domain.timetableDataManager.TimetableDataManagerImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class TimetableViewModel : MainViewModel() {
    private val timetableDataManager = TimetableDataManagerImpl()
    private val timetableDataParser = TimetableDataResponseToListOfTimetableDataParserImpl()
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

    private suspend fun syncCurrentLessonNumberWithCurrentTime() {
        while (true) {
            val calendar = Calendar.getInstance()
            val currentLesson =
                when (calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)) {
                    in 425..470 -> 0.0f
                    in 471..474 -> 0.5f

                    in 475..520 -> 1.0f
                    in 521..524 -> 1.5f

                    in 525..570 -> 2.0f
                    in 571..574 -> 2.5f

                    in 575..620 -> 3.0f
                    in 621..624 -> 3.5f

                    in 625..670 -> 4.0f

                    in 671..684 -> 420.0f

                    in 685..730 -> 5.0f
                    in 731..734 -> 5.5f

                    in 735..780 -> 6.0f
                    in 781..784 -> 6.5f

                    in 785..830 -> 7.0f
                    in 831..834 -> 7.5f

                    in 835..880 -> 8.0f
                    in 881..884 -> 8.5f

                    in 885..930 -> 9.0f
                    in 931..934 -> 9.5f

                    in 935..980 -> 10.0f
                    else -> -1.0f
                }
            if (currentLesson != _uiState.value.currentLesson)
                _uiState.update { it.copy(currentLesson = currentLesson) }
            delay(1000)
        }

    }

    init {
        viewModelScope.launch {
            refreshTimetableWithNewData(
                timetableDataManager.getTimetableData(directory, timetableDataParser)
            )
            syncCurrentLessonNumberWithCurrentTime()
        }
    }
}