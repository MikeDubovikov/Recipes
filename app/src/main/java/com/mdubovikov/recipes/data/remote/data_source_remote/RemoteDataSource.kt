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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MealDataSource.Remote {
    override suspend fun getMeals(category: String): MealResponse =
        withContext(ioDispatcher) {
            mealService.getMeals(category)
        }

    override suspend fun getMealDetails(mealId: Int): MealDetailsResponse =
        withContext(ioDispatcher) {
            mealService.getMealDetails(mealId)
        }

    override suspend fun searchMeals(query: String): MealResponse =
        withContext(ioDispatcher) {
            mealService.searchMeals(query)
        }

    override suspend fun getCategories(): CategoryResponse =
        withContext(ioDispatcher) {
            mealService.getCategories()
        }
}