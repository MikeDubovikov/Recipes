package com.mdubovikov.recipes.domain.repository

import com.mdubovikov.recipes.AppSettings
import com.mdubovikov.recipes.Language
import com.mdubovikov.recipes.Theme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

//    val theme: Flow<Theme?>
//
//    suspend fun setTheme(theme: Theme?)
//
//    val language: Flow<Language?>
//
//    suspend fun setLanguage(language: Language?)

    val settings: Flow<AppSettings>

    suspend fun setTheme(theme: Theme)

    suspend fun setLanguage(language: Language)
}