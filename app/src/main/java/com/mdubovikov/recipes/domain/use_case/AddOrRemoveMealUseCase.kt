package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.data.mapper.MealMapper
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import javax.inject.Inject

class AddOrRemoveMealUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val mealMapper: MealMapper,
    private val isMealInSavedUseCase: IsMealInSavedUseCase
) {
    suspend fun addOrRemoveMeal(meal: MealModel) {
        if (isMealInSavedUseCase.isMealInSaved(meal.id)) {
            mealRepository.removeFromSavedMeals(mealMapper.mapMealModelToMealEntity(meal.copy(isSaved = false)))
        } else {
            mealRepository.addToSavedMeals(mealMapper.mapMealModelToMealEntity(meal.copy(isSaved = true)))
        }
    }
}