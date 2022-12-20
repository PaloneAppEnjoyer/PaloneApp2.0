package com.palone.paloneapp.ui

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.substitutions.data.ScreensProperties
import com.palone.paloneapp.substitutions.data.models.SubstitutionsScreenUiState
import com.palone.paloneapp.substitutions.domain.substitutionsDataManager.SubstitutionsDataManagerImpl
import com.palone.paloneapp.utils.htmlParser.HtmlParserImpl
import com.palone.paloneapp.utils.timeManager.TimeManagerImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.io.File
import java.io.FileOutputStream
import java.util.*

class SubstitutionsViewModel : MainViewModel() {
    private val substitutionsDataManager = SubstitutionsDataManagerImpl()
    private val htmlParser = HtmlParserImpl()
    private val _uiState = MutableStateFlow(SubstitutionsScreenUiState())
    val uiState: StateFlow<SubstitutionsScreenUiState> = _uiState.asStateFlow()
    private val _currentCalendar = Calendar.getInstance()
    private val timeManager = TimeManagerImpl(_currentCalendar)

    private fun saveFilterQueryPreferences(query: String) {
        preferencesProvider.updateFilterQuery(query)
    }

    override suspend fun updateUiStateWithPreferences() {
        _uiState.update { it.copy(classFilter = preferencesProvider.filterQueryFlow.first()) }
    }


    fun updateSelectedLocalDate(date: LocalDate) {
        Log.i("timeee", date.toString())
        _uiState.update { it.copy(selectedLocalDate = date) }
    }

    private fun saveBitmapToInternalStorage(
        context: Context,
        bitmap: Bitmap,
        fileName: String
    ): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun onLongPressShare(
        bitmapFromComposable: () -> Bitmap,
        context: Context,
        shareImage: () -> Unit
    ) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            saveBitmapToInternalStorage(
                context = context,
                bitmapFromComposable.invoke(),
                "share.png"
            )
            _uiState.update { it.copy(isLoading = false) }
            shareImage()
        }
    }


    override fun onFabClick(navHostController: NavHostController) {
        navHostController.navigate(ScreensProperties.TimetableScreen.route)
    }

    override suspend fun openDrawer() {
        _uiState.value.scaffoldState.drawerState.open()
    }

    override suspend fun closeDrawer() {
        _uiState.value.scaffoldState.drawerState.close()
    }


    fun updateClassFilter(query: String) {
        _uiState.update { it.copy(classFilter = query) }
        saveFilterQueryPreferences(query)
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
                    substitutionsList = substitutionsDataManager.getSubstitutionsDataWithLocalDate(
                        htmlParser = htmlParser,
                        localDate
                    ),
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
                    substitutionsDataManager.getFilteredSubstitutionDataByQuery(
                        it, query
                    )
                }
            )
        }
    }


    init {

        val todayTimeManager = timeManager.getCurrentDate()
        val todayLocalDate = LocalDate(
            year = todayTimeManager.year,
            monthNumber = todayTimeManager.month,
            dayOfMonth = todayTimeManager.day_of_month
        )
        val tomorrowTimeManager = timeManager.getTomorrowDate()
        val tomorrowLocalDate = LocalDate(
            year = tomorrowTimeManager.year,
            monthNumber = tomorrowTimeManager.month,
            dayOfMonth = tomorrowTimeManager.day_of_month
        )



        if (_currentCalendar.get(Calendar.HOUR_OF_DAY) > 16)
            updateSelectedLocalDate(
                tomorrowLocalDate
            )
        else
            updateSelectedLocalDate(
                todayLocalDate
            )
//        updateSelectedLocalDate(
//            LocalDate(
//                year = timeManager.getCurrentDate().year,
//                monthNumber = timeManager.getCurrentDate().month,
//                dayOfMonth = timeManager.getCurrentDate().day_of_month
//            )
//        )
        refreshSubstitutionsDataWithLocalDate(
            _uiState.value.selectedLocalDate
        ) {
            refreshFilteredSubstitutionsWithQuery()
        }

    }
}
