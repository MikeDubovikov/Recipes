package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_AREA
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_FIRST_LETTER
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_INGREDIENT
import com.mdubovikov.recipes.common.QueryKeys.SEARCH_BY_NAME
import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend fun search(query: String, searchKey: String): Flow<Result<List<MealModel>>> = flow {
        try {
            emit(Result.Loading)
            val searchedMeals = when (searchKey) {
                SEARCH_BY_NAME -> mealRepository.searchMealsByName(query = query)
                SEARCH_BY_FIRST_LETTER -> mealRepository.searchMealsByFirstLetter(query = query)
                SEARCH_BY_AREA -> mealRepository.searchMealsByArea(query = query)
                SEARCH_BY_INGREDIENT -> mealRepository.searchMealsByIngredient(query = query)
                else -> TODO()
            }
            emit(Result.Success(data = searchedMeals))
        } catch (e: IOException) {
            emit(Result.Error(error = e.message))
        } catch (e: HttpException) {
            emit(Result.Error(error = e.message))
        }
    }
}