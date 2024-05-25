package com.mdubovikov.recipes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdubovikov.recipes.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM saved_meals")
    fun getSavedMeals(): Flow<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToSavedMeals(meal: MealEntity)

    @Delete
    suspend fun removeFromSavedMeals(meal: MealEntity)

    @Query("SELECT * FROM saved_meals WHERE id=:mealId")
    suspend fun isMealInSaved(mealId: String): MealEntity?
}