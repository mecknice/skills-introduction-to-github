package com.example.kalimani.Preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val ONBOARDED_KEY = booleanPreferencesKey("is_onboarded")
        private val LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    // Function to check if the user has completed onboarding
    val isOnboarded: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[ONBOARDED_KEY] ?: false
        }

    // Function to check if the user is logged in
    val isLoggedIn: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[LOGGED_IN_KEY] ?: false
        }

    // Function to set the onboarding state
    suspend fun setOnboarded(isOnboarded: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDED_KEY] = isOnboarded
        }
    }

    // Function to set the logged-in state
    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[LOGGED_IN_KEY] = isLoggedIn
        }
    }
}
