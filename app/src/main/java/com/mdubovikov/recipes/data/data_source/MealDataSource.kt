package com.mdubovikov.recipes.data.data_source

import com.mdubovikov.recipes.data.local.entity.MealEntity
import com.mdubovikov.recipes.data.remote.dto.CategoryResponse
import com.mdubovikov.recipes.data.remote.dto.MealDetailsResponse
import com.mdubovikov.recipes.data.remote.dto.MealResponse
import kotlinx.coroutines.flow.Flow

interface MealDataSource {

    interface Remote {
        suspend fun getMeals(category: String): MealResponse
        suspend fun getMealDetails(mealId: Int): MealDetailsResponse
        suspend fun searchMealsByName(query: String): MealResponse
        suspend fun searchMealsByArea(query: String): MealResponse
        suspend fun searchMealsByFirstLetter(query: String): MealResponse
        suspend fun searchMealsByIngredient(query: String): MealResponse
        suspend fun getCategories(): CategoryResponse
    }

    interface Local {
        suspend fun addToSavedMeals(meal: MealEntity)
        suspend fun removeFromSavedMeals(meal: MealEntity)
        fun getSavedMeals(): Flow<List<MealEntity>>
        suspend fun isMealInSaved(mealId: String): Boolean
    }
}