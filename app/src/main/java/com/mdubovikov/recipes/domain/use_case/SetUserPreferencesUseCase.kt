package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetUserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun setTheme(theme: Int) = userPreferencesRepository.setTheme(theme = theme)
    suspend fun setLanguage(language: Int) = userPreferencesRepository.setLanguage(language = language)
}