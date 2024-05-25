package com.mdubovikov.recipes.data.remote.service

import com.mdubovikov.recipes.common.Constants
import com.mdubovikov.recipes.common.Constants.Queries.MEALS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.MEAL_DETAILS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_QUERY
import com.mdubovikov.recipes.data.remote.dto.CategoryResponse
import com.mdubovikov.recipes.data.remote.dto.MealDetailResponse
import com.mdubovikov.recipes.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET(Constants.Endpoints.MEALS)
    suspend fun getMeals(@Query(MEALS_QUERY) category: String): MealResponse

    @GET(Constants.Endpoints.MEAL_DETAILS)
    suspend fun getMealDetails(@Query(MEAL_DETAILS_QUERY) id: Int): MealDetailResponse

    @GET(Constants.Endpoints.SEARCH_MEALS)
    suspend fun searchMeal(@Query(SEARCH_QUERY) query: String): MealResponse

    @GET(Constants.Endpoints.CATEGORIES)
    suspend fun getCategories(): CategoryResponse
}