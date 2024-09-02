package com.mdubovikov.recipes.domain.repository

interface UserPreferencesRepository {

    suspend fun getTheme(): Int

    suspend fun setTheme(theme: Int)

    suspend fun getLanguage(): Int

    suspend fun setLanguage(language: Int)
}