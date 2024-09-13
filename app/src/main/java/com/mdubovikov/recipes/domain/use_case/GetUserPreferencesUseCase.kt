package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.AppSettings
import com.mdubovikov.recipes.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) {
    val settings: Flow<AppSettings> = userPreferencesRepository.settings
}