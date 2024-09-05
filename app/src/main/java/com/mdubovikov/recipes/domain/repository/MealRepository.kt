package com.mdubovikov.recipes.domain.repository

import com.mdubovikov.recipes.data.local.entity.MealEntity
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.model.MealModel
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun addToSavedMeals(meal: MealEntity)

    suspend fun removeFromSavedMeals(meal: MealEntity)

    fun getSavedMeals(): Flow<List<MealEntity>>

    suspend fun isMealInSaved(mealId: String): Boolean

    suspend fun getMeals(category: String): List<MealModel>

    suspend fun getMealDetails(mealId: Int): MealDetailsModel

    suspend fun searchMealsByName(query: String): List<MealModel>

    suspend fun searchMealsByArea(query: String): List<MealModel>

    suspend fun searchMealsByFirstLetter(query: String): List<MealModel>

    suspend fun searchMealsByIngredient(query: String): List<MealModel>

    suspend fun getCategories(): List<CategoryModel>
}