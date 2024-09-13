package com.mdubovikov.recipes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.mdubovikov.recipes.AppSettings
import com.mdubovikov.recipes.Language
import com.mdubovikov.recipes.Theme
import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userDataStorePreferences: DataStore<AppSettings>
) : UserPreferencesRepository {

    override val settings: Flow<AppSettings> = userDataStorePreferences.data
        .catch { exception ->
            if (exception is IOException) {
                emit(AppSettings.getDefaultInstance())
            } else {
                throw exception
            }
        }

    override suspend fun setTheme(theme: Theme) {
        userDataStorePreferences.updateData { preferences ->
            preferences.toBuilder().setTheme(theme).build()
        }
    }

    override suspend fun setLanguage(language: Language) {
        userDataStorePreferences.updateData { preferences ->
            preferences.toBuilder().setLanguage(language).build()
        }
    }
}