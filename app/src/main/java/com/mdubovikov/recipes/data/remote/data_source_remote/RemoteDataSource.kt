package com.mdubovikov.recipes.data.remote.data_source_remote

import com.mdubovikov.recipes.data.data_source.MealDataSource
import com.mdubovikov.recipes.data.remote.dto.CategoryResponse
import com.mdubovikov.recipes.data.remote.dto.MealDetailsResponse
import com.mdubovikov.recipes.data.remote.dto.MealResponse
import com.mdubovikov.recipes.data.remote.service.MealService
import com.mdubovikov.recipes.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val mealService: MealService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MealDataSource.Remote {
    override suspend fun getMeals(category: String): MealResponse =
        withContext(dispatcher) {
            mealService.getMeals(category)
        }

    override suspend fun getRandomMeals(): MealResponse =
        withContext(dispatcher) {
            mealService.getRandomMeals()
        }

    override suspend fun getMealDetails(mealId: Int): MealDetailsResponse =
        withContext(dispatcher) {
            mealService.getMealDetails(mealId)
        }

    override suspend fun searchMealsByName(query: String): MealResponse =
        withContext(dispatcher) {
            mealService.searchMealsByName(query)
        }

    override suspend fun searchMealsByArea(query: String): MealResponse =
        withContext(dispatcher) {
            mealService.searchMealsByArea(query)
        }

    override suspend fun searchMealsByFirstLetter(query: String): MealResponse =
        withContext(dispatcher) {
            mealService.searchMealsByFirstLetter(query)
        }

    override suspend fun searchMealsByIngredient(query: String): MealResponse =
        withContext(dispatcher) {
            mealService.searchMealsByIngredient(query)
        }

    override suspend fun getCategories(): CategoryResponse =
        withContext(dispatcher) {
            mealService.getCategories()
        }
}