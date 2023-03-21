package com.palone.paloneapp.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.data.ScreensProperties
import com.palone.paloneapp.substitutions.data.models.SubstitutionData
import com.palone.paloneapp.substitutions.data.models.SubstitutionDataEntry
import com.palone.paloneapp.substitutions.domain.substitutionsDataManager.SubstitutionsDataManagerImpl
import com.palone.paloneapp.timetable.data.models.TimetableData
import com.palone.paloneapp.timetable.data.models.TimetableLessons
import com.palone.paloneapp.timetable.data.models.TimetableScreenUiState
import com.palone.paloneapp.timetable.domain.timetableDataManager.TimetableDataManagerImpl
import com.palone.paloneapp.timetable.useCases.LessonToSubstitutionsProvider
import com.palone.paloneapp.timetable.useCases.LessonToSubstitutionsProviderImpl
import com.palone.paloneapp.utils.htmlParser.HtmlParserImpl
import com.palone.paloneapp.utils.timeManager.TimeManagerImpl
import com.palone.paloneapp.utils.timetableDataResponseToListOfTimetableDataParser.TimetableDataResponseToListOfTimetableDataParserImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.*

class TimetableViewModel(private val lessonToSubstitutionsProvider: LessonToSubstitutionsProvider = LessonToSubstitutionsProviderImpl()) :
    MainViewModel() {
    private val timetableDataManager = TimetableDataManagerImpl()
    private val timetableDataParser = TimetableDataResponseToListOfTimetableDataParserImpl()

    private val substitutionsDataManager = SubstitutionsDataManagerImpl()
    private val htmlParser = HtmlParserImpl()


    private val _uiState = MutableStateFlow(TimetableScreenUiState())
    val uiState: StateFlow<TimetableScreenUiState> = _uiState.asStateFlow()


    private var substitutionsToday: List<SubstitutionData> = emptyList()
    private var substitutionsTomorrow: List<SubstitutionData> = emptyList()

    private val _currentCalendar = Calendar.getInstance()

    private val timeManager = TimeManagerImpl(_currentCalendar)
    private val currentDate = timeManager.getCurrentDate()
    private val tomorrowDate = timeManager.getTomorrowDate()

    private var timetableList: List<TimetableData> = emptyList()
    private var allSchoolClassNames: MutableList<String> = mutableListOf()

    private fun saveSchoolClassPreferences(schoolClassName: String) {
        preferencesProvider.updateSchoolClass(schoolClassName)
    }

    private fun saveHiddenGroupsFilter(groups: List<String>) {
        preferencesProvider.updateHiddenGroupsFilter(groups.toSet())
    }

    override suspend fun updateUiStateWithPreferences() {
        _uiState.update {
            it.copy(
                selectedSchoolClass = preferencesProvider.schoolClassFlow.first(),
                hiddenGroups = preferencesProvider.hiddenGroupsFilterFlow.first().toList()
            )
        }
    }

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
        _uiState.update { it.copy(isLoading = true) }
        val currentHiddenGroups = _uiState.value.hiddenGroups.toMutableList()
        currentHiddenGroups.add(group)
        _uiState.update {
            it.copy(
                hiddenGroups = currentHiddenGroups,
                lessonsList = getTimetableLessons(),
                isLoading = false
            )
        }
        saveHiddenGroupsFilter(currentHiddenGroups)
    }

    fun unsetThisGroupHidden(group: String) {
        _uiState.update { it.copy(isLoading = true) }
        val currentHiddenGroups = _uiState.value.hiddenGroups.toMutableList()
        currentHiddenGroups.remove(group)
        _uiState.update {
            it.copy(
                hiddenGroups = currentHiddenGroups,
                lessonsList = getTimetableLessons(),
                isLoading = false
            )
        }
        saveHiddenGroupsFilter(currentHiddenGroups)
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
        saveSchoolClassPreferences(schoolClassName)
    }

    private fun getTimetableLessons(): List<TimetableLessons> {
        timetableList.sortedBy { it.className }
            .filter { it.className == _uiState.value.selectedSchoolClass }.forEach {
                it.day.filter { it2 -> it2.dayNameShorted == _uiState.value.selectedDay }
                    .forEach { it2 ->
                        Log.i("HEY", "1")

                        return it2.lessons
                    }
            }
        Log.i("HEY", "2")
        return emptyList()
    }

    private fun refreshTimetableWithNewData(data: List<TimetableData>) {
        _uiState.update { it.copy(isLoading = true) }
        timetableList = data
        allSchoolClassNames.clear()
        timetableList.forEach { allSchoolClassNames.add(it.className) }
        _uiState.update { it.copy(lessonsList = getTimetableLessons(), isLoading = false) }
    }

    fun selectDay(day: String) {
        _uiState.update { it.copy(selectedDay = day, isLoading = true) }
        _uiState.update { it.copy(lessonsList = getTimetableLessons(), isLoading = false) }
    }


    fun getSubstitutionsForSelectedLesson(lessonNumber: Int): Flow<List<SubstitutionDataEntry>> =
        flow {
            while (substitutionsTomorrow.isEmpty() && substitutionsToday.isEmpty()) {
                emit(emptyList())
                delay(1000)
            }
            val data =
                if (substitutionsToday.isNotEmpty() && _uiState.value.selectedDay == currentDate.day_of_week_name && substitutionsToday[0].entries[0].teacherReplacement != "  W tym dniu nie ma żadnych zastępstw") substitutionsToday
                else if (substitutionsTomorrow.isNotEmpty() && _uiState.value.selectedDay == tomorrowDate.day_of_week_name && substitutionsTomorrow[0].entries[0].teacherReplacement != "  W tym dniu nie ma żadnych zastępstw") substitutionsTomorrow
                else emptyList()
            emit(
                lessonToSubstitutionsProvider.getSubstitutionsFromLessonNumber(
                    lessonNumber,
                    data,
                    _uiState.value.selectedSchoolClass
                )
            )
        }


    private suspend fun syncCurrentLessonNumberWithCurrentTime() {//todo wrzucić do usecase
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
                    in 671..684 -> 4.5f // 15 min

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

    private suspend fun syncCurrentLessonProgressWithCurrentTime() {//todo wrzucić do usecase
        while (true) {
            val calendar = Calendar.getInstance()
            val currentDayMinutes =
                (calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE))
            val currentLessonProgress =
                when (_uiState.value.currentLesson) {
                    0f -> ((currentDayMinutes - 425) * 100) / 45f
                    1f -> ((currentDayMinutes - 475) * 100) / 45f
                    2f -> ((currentDayMinutes - 525) * 100) / 45f
                    3f -> ((currentDayMinutes - 575) * 100) / 45f
                    4f -> ((currentDayMinutes - 625) * 100) / 45f
                    5f -> ((currentDayMinutes - 685) * 100) / 45f
                    6f -> ((currentDayMinutes - 735) * 100) / 45f
                    7f -> ((currentDayMinutes - 785) * 100) / 45f
                    8f -> ((currentDayMinutes - 835) * 100) / 45f
                    9f -> ((currentDayMinutes - 885) * 100) / 45f
                    10f -> ((currentDayMinutes - 935) * 100) / 45f
                    else -> -1.0f
                }
            _uiState.update { it.copy(currentLessonProgress = currentLessonProgress) }
            delay(1000)
        }

    }

    override fun initializer() { //TODO zrobić dependency injection i wsadzić preferences provider jako argument
        viewModelScope.launch {
            updateUiStateWithPreferences()
            _uiState.update { it.copy(todayDate = currentDate) }
            selectDay(if (_currentCalendar.get(Calendar.HOUR_OF_DAY) > 16) timeManager.getTomorrowDate().day_of_week_name else timeManager.getCurrentDate().day_of_week_name)
            launch {
                timetableDataManager.getTimetableData(directory, timetableDataParser)
                    .collect { value ->
                        refreshTimetableWithNewData(
                            value
                        )
                    }
            }
            launch {
                substitutionsToday = substitutionsDataManager.getSubstitutionsDataWithLocalDate(
                    htmlParser,
                    LocalDate(currentDate.year, currentDate.month, currentDate.day_of_month)
                )
            }
            launch {
                substitutionsTomorrow = substitutionsDataManager.getSubstitutionsDataWithLocalDate(
                    htmlParser,
                    LocalDate(tomorrowDate.year, tomorrowDate.month, tomorrowDate.day_of_month)
                )
            }

            launch { syncCurrentLessonProgressWithCurrentTime() }
            launch { syncCurrentLessonNumberWithCurrentTime() }
        }
    }

    init {

    }
}