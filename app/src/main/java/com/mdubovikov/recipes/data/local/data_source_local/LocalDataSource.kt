package com.mdubovikov.recipes.data.local.data_source_local

import com.mdubovikov.recipes.data.data_source.MealDataSource
import com.mdubovikov.recipes.data.local.dao.MealDao
import com.mdubovikov.recipes.data.local.entity.MealEntity
import com.mdubovikov.recipes.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val mealDao: MealDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MealDataSource.Local {

    override suspend fun addToSavedMeals(meal: MealEntity) =
        withContext(dispatcher) {
            mealDao.addToSavedMeals(meal)
        }

    override suspend fun removeFromSavedMeals(meal: MealEntity) =
        withContext(dispatcher) {
            mealDao.removeFromSavedMeals(meal)
        }

    override fun getSavedMeals(): Flow<List<MealEntity>> = mealDao.getSavedMeals()

    override suspend fun isMealInSaved(mealId: String): Boolean =
        withContext(Dispatchers.IO) {
            mealDao.isMealInSaved(mealId) != null
        }
}