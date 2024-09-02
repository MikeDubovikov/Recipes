package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun getTheme() = userPreferencesRepository.getTheme()
    suspend fun getLanguage() = userPreferencesRepository.getLanguage()
}