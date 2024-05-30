package com.mdubovikov.recipes.common.data_store

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull

suspend fun DataCoordinator.getTheme(): Int {
    val context = this.context ?: return defaultTheme
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.theme)
        ?: defaultTheme
}

suspend fun DataCoordinator.setTheme(value: Int) {
    val context = this.context ?: return
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.theme] = value
    }
}

suspend fun DataCoordinator.getLanguage(): Int {
    val context = this.context ?: return defaultLanguage
    return context.dataStore.data.firstOrNull()?.get(PreferencesKeys.language)
        ?: defaultLanguage
}

suspend fun DataCoordinator.setLanguage(value: Int) {
    val context = this.context ?: return
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.language] = value
    }
}