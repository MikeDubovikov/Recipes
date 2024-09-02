package com.mdubovikov.recipes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<Preferences>
) : UserPreferencesRepository {

    override suspend fun getTheme() =
        userDataStorePreferences.data.first()[THEME] ?: DEFAULT_THEME

    override suspend fun setTheme(theme: Int) {
        userDataStorePreferences.edit { settings ->
            settings[THEME] = theme
        }
    }

    override suspend fun getLanguage() =
        userDataStorePreferences.data.first()[LANGUAGE] ?: DEFAULT_LANGUAGE

    override suspend fun setLanguage(language: Int) {
        userDataStorePreferences.edit { settings ->
            settings[LANGUAGE] = language
        }
    }

    companion object {
        private val THEME = intPreferencesKey("theme")
        private val LANGUAGE = intPreferencesKey("language")
        private const val DEFAULT_THEME = 2
        private const val DEFAULT_LANGUAGE = 2
    }
}