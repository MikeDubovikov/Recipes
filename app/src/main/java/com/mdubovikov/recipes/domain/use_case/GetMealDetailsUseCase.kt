package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.MealDetailsModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMealDetailsUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(mealId: Int): Flow<Result<MealDetailsModel>> = flow {
        try {
            emit(Result.Loading)
            val meal = mealRepository.getMealDetails(mealId = mealId)
            emit(Result.Success(data = meal))
        } catch (e: IOException) {
            emit(Result.Error(error = e.message))
        } catch (e: HttpException) {
            emit(Result.Error(error = e.message))
        }
    }
}