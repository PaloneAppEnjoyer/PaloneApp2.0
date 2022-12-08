package com.palone.paloneapp.ui

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.substitutions.data.ScreensProperties
import com.palone.paloneapp.substitutions.data.models.SubstitutionsScreenUiState
import com.palone.paloneapp.substitutions.domain.substitutionsDataManager.SubstitutionsDataManagerImpl
import com.palone.paloneapp.utils.htmlParser.HtmlParserImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.util.*

class SubstitutionsViewModel : MainViewModel() {
    private val substitutionsDataManager = SubstitutionsDataManagerImpl()
    private val htmlParser = HtmlParserImpl()
    private val _uiState = MutableStateFlow(SubstitutionsScreenUiState())
    val uiState: StateFlow<SubstitutionsScreenUiState> = _uiState.asStateFlow()
    private val _currentCalendar = MutableStateFlow(Calendar.getInstance())

    fun updateSelectedLocalDate(date: LocalDate) {
        _uiState.update { it.copy(selectedLocalDate = date) }
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
