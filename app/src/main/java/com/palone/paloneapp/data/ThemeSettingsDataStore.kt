package com.palone.paloneapp.settings.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.themeDataStore by preferencesDataStore("theme_settings")

object ThemeKeys {
    val SURFACE = intPreferencesKey("surface")
}

class ThemeSettingsRepository(private val context: Context) {

    val surface = context.themeDataStore.data.map {
        it[ThemeKeys.SURFACE] ?: 0xFF03A9F4.toInt()
    }

    suspend fun setSurfaceColor(value: Int) {
        context.themeDataStore.edit {
            it[ThemeKeys.SURFACE] = value
        }
    }
    suspend fun resetSurfaceColor() {
        context.themeDataStore.edit {
            it.remove(ThemeKeys.SURFACE)
        }
    }

}
