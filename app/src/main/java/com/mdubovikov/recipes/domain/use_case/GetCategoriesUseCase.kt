package com.mdubovikov.recipes.domain.use_case

import com.mdubovikov.recipes.common.Result
import com.mdubovikov.recipes.domain.model.CategoryModel
import com.mdubovikov.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(): Flow<Result<List<CategoryModel>>> = flow {
        try {
            emit(Result.Loading)
            val categories = mealRepository.getCategories()
            emit(Result.Success(data = categories))
        } catch (e: IOException) {
            emit(Result.Error(error = e.message))
        } catch (e: HttpException) {
            emit(Result.Error(error = e.message))
        }
    }
}