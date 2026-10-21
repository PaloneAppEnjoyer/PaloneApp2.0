package com.palone.paloneapp.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("app_settings")

object SettingsKeys {
    val NOTIF_ZASTEPSTWA = booleanPreferencesKey("notif_zastepstwa")
    val NOTIF_PLAN = booleanPreferencesKey("notif_plan")
    val NOTIF_LEKCJA = booleanPreferencesKey("notif_lekcja")
}

class SettingsRepository(private val context: Context) {

    val notifZastepstwa = context.dataStore.data.map { it[SettingsKeys.NOTIF_ZASTEPSTWA] ?: true }
    val notifPlan = context.dataStore.data.map { it[SettingsKeys.NOTIF_PLAN] ?: false }
    val notifLekcja = context.dataStore.data.map { it[SettingsKeys.NOTIF_LEKCJA] ?: true }

    suspend fun setNotifZastepstwa(value: Boolean) {
        context.dataStore.edit { it[SettingsKeys.NOTIF_ZASTEPSTWA] = value }
    }

    suspend fun setNotifPlan(value: Boolean) {
        context.dataStore.edit { it[SettingsKeys.NOTIF_PLAN] = value }
    }

    suspend fun setNotifLekcja(value: Boolean) {
        context.dataStore.edit { it[SettingsKeys.NOTIF_LEKCJA] = value }
    }
}
