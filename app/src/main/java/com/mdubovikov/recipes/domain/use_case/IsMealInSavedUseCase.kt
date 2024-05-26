package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.domain.repository.MealRepository
import javax.inject.Inject

class IsMealInSavedUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend fun isMealInSaved(mealId: String): Boolean = mealRepository.isMealInSaved(mealId)
}