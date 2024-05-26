package com.mdubovikov.recipes.data.remote.service

import com.mdubovikov.recipes.common.Constants.Endpoints.CATEGORIES
import com.mdubovikov.recipes.common.Constants.Endpoints.MEALS
import com.mdubovikov.recipes.common.Constants.Endpoints.MEAL_DETAILS
import com.mdubovikov.recipes.common.Constants.Endpoints.SEARCH_MEALS
import com.mdubovikov.recipes.common.Constants.Queries.MEALS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.MEAL_DETAILS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_QUERY
import com.mdubovikov.recipes.data.remote.dto.CategoryResponse
import com.mdubovikov.recipes.data.remote.dto.MealDetailsResponse
import com.mdubovikov.recipes.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET(MEALS)
    suspend fun getMeals(@Query(MEALS_QUERY) category: String): MealResponse

    @GET(MEAL_DETAILS)
    suspend fun getMealDetails(@Query(MEAL_DETAILS_QUERY) id: Int): MealDetailsResponse

    @GET(SEARCH_MEALS)
    suspend fun searchMeal(@Query(SEARCH_QUERY) query: String): MealResponse

    @GET(CATEGORIES)
    suspend fun getCategories(): CategoryResponse
}