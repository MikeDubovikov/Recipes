package com.mdubovikov.recipes.common.data_store

import androidx.datastore.preferences.core.intPreferencesKey

object PreferencesKeys {
    val theme = intPreferencesKey("theme")
    val language = intPreferencesKey("language")
}