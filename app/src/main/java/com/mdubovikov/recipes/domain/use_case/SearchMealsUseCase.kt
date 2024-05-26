package com.mdubovikov.recipes.domain.use_case

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
    operator fun invoke(query: String): Flow<Result<List<MealModel>>> = flow {
        try {
            emit(Result.Loading)
            val searchedMeals = mealRepository.searchMeals(query = query)
            emit(Result.Success(data = searchedMeals))
        } catch (e: IOException) {
            emit(Result.Error(error = e.message))
        } catch (e: HttpException) {
            emit(Result.Error(error = e.message))
        }
    }
}