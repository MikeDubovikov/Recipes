package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.MealModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(category: String): Flow<Result<List<MealModel>>> = flow {
        try {
            emit(Result.Loading)
            val meals = mealRepository.getMeals(category = category)
            emit(Result.Success(data = meals))
        } catch (e: IOException) {
            emit(Result.Error(error = e.message))
        } catch (e: HttpException) {
            emit(Result.Error(error = e.message))
        }
    }
}