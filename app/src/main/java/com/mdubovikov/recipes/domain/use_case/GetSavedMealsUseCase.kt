package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.data.mapper.MealMapper
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSavedMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val mealMapper: MealMapper
) {
    operator fun invoke(): Flow<List<MealModel>> {
        val mealsSaved = mealRepository.getSavedMeals()
        return mealsSaved.map {
            it.map { mealEntity -> mealMapper.mapMealEntityToMealModel(mealEntity) }
        }
    }
}