package com.shuham.medilog.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Preferences Keys
 */
object PreferencesKeys {
    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    val USER_LOGGED_IN = booleanPreferencesKey("user_logged_in")
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val IS_GUEST = booleanPreferencesKey("is_guest")
    val DARK_THEME = booleanPreferencesKey("dark_theme")
}

/**
 * Repository for app preferences
 * Handles onboarding state, user session, and app settings
 */
class PreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    /**
     * Check if onboarding has been completed
     */
    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] ?: false
        }
    }
    
    /**
     * Set onboarding completed
     */
    suspend fun setOnboardingCompleted(completed: Boolean = true) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.ONBOARDING_COMPLETED] = completed
        }
    }
    
    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_LOGGED_IN] ?: false
        }
    }
    
    /**
     * Get user ID
     */
    fun getUserId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_ID]
        }
    }
    
    /**
     * Get user name
     */
    fun getUserName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_NAME]
        }
    }
    
    /**
     * Get user email
     */
    fun getUserEmail(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.USER_EMAIL]
        }
    }
    
    /**
     * Check if user is guest
     */
    fun isGuest(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_GUEST] ?: false
        }
    }
    
    /**
     * Save user session
     */
    suspend fun saveUserSession(
        userId: String,
        name: String,
        email: String,
        isGuest: Boolean = false
    ) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_LOGGED_IN] = true
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.USER_EMAIL] = email
            preferences[PreferencesKeys.IS_GUEST] = isGuest
        }
    }
    
    /**
     * Clear user session (logout)
     */
    suspend fun clearUserSession() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_LOGGED_IN] = false
            preferences.remove(PreferencesKeys.USER_ID)
            preferences.remove(PreferencesKeys.USER_NAME)
            preferences.remove(PreferencesKeys.USER_EMAIL)
            preferences[PreferencesKeys.IS_GUEST] = false
        }
    }
    
    /**
     * Get dark theme preference
     */
    fun isDarkTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] ?: false
        }
    }
    
    /**
     * Set dark theme preference
     */
    suspend fun setDarkTheme(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = enabled
        }
    }
}
