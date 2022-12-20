package com.palone.paloneapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private object Preference {
        val FILTER_QUERY = stringPreferencesKey("filter_query")
        val SCHOOL_CLASS = stringPreferencesKey("school_class")
        val LAST_SCREEN = stringPreferencesKey("screen")
        val HIDDEN_GROUPS = stringPreferencesKey("hidden_groups")
    }

    suspend fun updateFilterQuery(query: String) {
        dataStore.edit { preferences ->
            preferences[Preference.FILTER_QUERY] = query
            Log.i("info", "saving filter query")
        }
    }

    suspend fun updateSchoolClass(schoolClass: String) {
        dataStore.edit { preferences ->
            preferences[Preference.SCHOOL_CLASS] = schoolClass
            Log.i("info", "saving school class")
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
            Log.i("info", "sending filter query")

            preferences.get(Preference.FILTER_QUERY) ?: ""
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
            Log.i("info", "sending school class")
            preferences.get(Preference.SCHOOL_CLASS) ?: ""
        }


}