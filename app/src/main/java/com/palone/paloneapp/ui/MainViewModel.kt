package com.palone.paloneapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.palone.paloneapp.data.UserPreferencesRepository
import kotlinx.coroutines.launch


sealed class MainViewModel : ViewModel() {
    abstract suspend fun openDrawer()
    abstract suspend fun closeDrawer()
    abstract suspend fun updateUiStateWithPreferences()
    abstract fun onFabClick(navHostController: NavHostController)
    val directory = "/data/user/0/com.palone.paloneapp/files" //TODO("can't do this like that")
    lateinit var preferencesRepository: UserPreferencesRepository
    fun setUserPreferencesRepository(
        userPreferencesRepository: UserPreferencesRepository,
        then: () -> Unit = {}
    ) {
        preferencesRepository = userPreferencesRepository
        viewModelScope.launch {
            updateUiStateWithPreferences()
            then()
        }
    }
}