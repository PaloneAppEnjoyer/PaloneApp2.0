package com.palone.paloneapp.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.palone.paloneapp.utils.PreferencesProvider.PreferencesProviderImpl


abstract class MainViewModel : ViewModel() {
    abstract suspend fun openDrawer()
    abstract suspend fun closeDrawer()
    abstract suspend fun updateUiStateWithPreferences()
    abstract fun initializer()
    abstract fun onFabClick(navHostController: NavHostController)
    val directory = "/data/user/0/com.palone.paloneapp/files" //TODO("can't do this like that")
    lateinit var preferencesProvider: PreferencesProviderImpl
    fun updatePreferencesProvider(
        preferencesProviderImpl: PreferencesProviderImpl
    ) {
        this.preferencesProvider = preferencesProviderImpl
        preferencesProvider.runInScope { updateUiStateWithPreferences() }
        initializer()
    }
}