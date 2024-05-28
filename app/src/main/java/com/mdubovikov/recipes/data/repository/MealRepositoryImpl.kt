package com.mdubovikov.recipes.data.repository

import com.mdubovikov.recipes.data.data_source.MealDataSource
import com.mdubovikov.recipes.data.local.entity.MealEntity
import com.mdubovikov.recipes.data.mapper.MealMapper
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val local: MealDataSource.Local,
    private val remote: MealDataSource.Remote,
    private val mapper: MealMapper
) : MealRepository {

    override suspend fun addToSavedMeals(meal: MealEntity) {
        return local.addToSavedMeals(meal)
    }

    override suspend fun removeFromSavedMeals(meal: MealEntity) {
        return local.removeFromSavedMeals(meal)
    }

    override fun getSavedMeals(): Flow<List<MealEntity>> {
        return local.getSavedMeals()
    }

    override suspend fun isMealInSaved(mealId: String): Boolean {
        return local.isMealInSaved(mealId)
    }

    override suspend fun getMeals(category: String): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.getMeals(category).meals)
    }

    override suspend fun getRandomMeals(): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.getRandomMeals().meals)
    }

    override suspend fun getMealDetails(mealId: Int): MealDetailsModel {
        return mapper.mapMealDetailsDtoToMealDetailsModel(remote.getMealDetails(mealId).meals[0])
    }

    override suspend fun searchMealsByName(query: String): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.searchMealsByName(query).meals)
    }

    override suspend fun searchMealsByArea(query: String): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.searchMealsByArea(query).meals)
    }

    override suspend fun searchMealsByFirstLetter(query: String): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.searchMealsByFirstLetter(query).meals)
    }

    override suspend fun searchMealsByIngredient(query: String): List<MealModel> {
        return mapper.mapListMealDtoToListMealModel(remote.searchMealsByIngredient(query).meals)
    }

    override suspend fun getCategories(): List<CategoryModel> {
        return mapper.mapListCategoryDtoToListCategoryModel(remote.getCategories().categories)
    }
}