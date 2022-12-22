package com.palone.paloneapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private object Preference {
        val FILTER_QUERY = stringPreferencesKey("filter_query")
        val SCHOOL_CLASS = stringPreferencesKey("school_class")
        val LAST_SCREEN = stringPreferencesKey("screen")
        val HIDDEN_GROUPS_FILTER = stringSetPreferencesKey("hidden_groups")
    }

    suspend fun updateFilterQuery(query: String) {
        dataStore.edit { preferences ->
            preferences[Preference.FILTER_QUERY] = query
        }
    }

    suspend fun updateHiddenGroupsFilter(groups: Set<String>) {
        dataStore.edit { preferences ->
            preferences[Preference.HIDDEN_GROUPS_FILTER] = groups
        }
    }

    suspend fun updateSchoolClass(schoolClass: String) {
        dataStore.edit { preferences ->
            preferences[Preference.SCHOOL_CLASS] = schoolClass
        }
    }

    suspend fun updateLastScreen(screenName: String) {
        dataStore.edit { preferences ->
            preferences[Preference.LAST_SCREEN] = screenName
        }
    }

    val filterQueryFlow: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences.get(Preference.FILTER_QUERY) ?: ""
        }
    val hiddenGroupsFilterFlow: Flow<Set<String>> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences.get(Preference.HIDDEN_GROUPS_FILTER) ?: setOf("")
        }

    val schoolClassFlow: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences.get(Preference.SCHOOL_CLASS) ?: ""
        }


}