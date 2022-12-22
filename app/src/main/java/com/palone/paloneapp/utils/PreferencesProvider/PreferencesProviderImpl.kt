package com.palone.paloneapp.utils.PreferencesProvider

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.palone.paloneapp.data.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PreferencesProviderImpl(context: Context, private val scope: CoroutineScope) {
    companion object {
        val Context.dataStore by preferencesDataStore(name = "user_preferences")
    }

    private val preferencesRepository: UserPreferencesRepository =
        UserPreferencesRepository(context.dataStore)
    var currentJob: Job? = null
    fun updateFilterQuery(query: String) {
//        currentJob?.cancel()
        currentJob = scope.launch { preferencesRepository.updateFilterQuery(query) }
    }

    fun updateSchoolClass(schoolClass: String) {
//        currentJob?.cancel()
        currentJob = scope.launch { preferencesRepository.updateSchoolClass(schoolClass) }
    }

    fun updateHiddenGroupsFilter(groups: Set<String>) {
        currentJob = scope.launch { preferencesRepository.updateHiddenGroupsFilter(groups) }
    }

    val hiddenGroupsFilterFlow = preferencesRepository.hiddenGroupsFilterFlow
    val filterQueryFlow = preferencesRepository.filterQueryFlow
    val schoolClassFlow = preferencesRepository.schoolClassFlow
    fun runInScope(run: suspend () -> Unit) {
//        currentJob?.cancel()
        currentJob = scope.launch { run() }
    }

}