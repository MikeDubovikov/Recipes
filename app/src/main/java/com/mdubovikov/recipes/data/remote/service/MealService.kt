package com.mdubovikov.recipes.data.remote.service

import com.mdubovikov.recipes.common.Constants.Endpoints.CATEGORIES
import com.mdubovikov.recipes.common.Constants.Endpoints.MEALS
import com.mdubovikov.recipes.common.Constants.Endpoints.MEAL_DETAILS
import com.mdubovikov.recipes.common.Constants.Endpoints.RANDOM_MEAL
import com.mdubovikov.recipes.common.Constants.Endpoints.SEARCH_MEALS
import com.mdubovikov.recipes.common.Constants.Queries.MEALS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.MEAL_DETAILS_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_AREA_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_FIRST_LETTER_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_INGREDIENT_QUERY
import com.mdubovikov.recipes.common.Constants.Queries.SEARCH_BY_NAME_QUERY
import com.mdubovikov.recipes.data.remote.dto.CategoryResponse
import com.mdubovikov.recipes.data.remote.dto.MealDetailsResponse
import com.mdubovikov.recipes.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {

    @GET(MEALS)
    suspend fun getMeals(@Query(MEALS_QUERY) category: String): MealResponse

    @GET(RANDOM_MEAL)
    suspend fun getRandomMeals(): MealResponse

    @GET(MEAL_DETAILS)
    suspend fun getMealDetails(@Query(MEAL_DETAILS_QUERY) id: Int): MealDetailsResponse

    @GET(SEARCH_MEALS)
    suspend fun searchMealsByName(@Query(SEARCH_BY_NAME_QUERY) query: String): MealResponse

    @GET(SEARCH_MEALS)
    suspend fun searchMealsByFirstLetter(@Query(SEARCH_BY_FIRST_LETTER_QUERY) query: String): MealResponse

    @GET(MEALS)
    suspend fun searchMealsByIngredient(@Query(SEARCH_BY_INGREDIENT_QUERY) ingredient: String): MealResponse

    @GET(MEALS)
    suspend fun searchMealsByArea(@Query(SEARCH_BY_AREA_QUERY) area: String): MealResponse

    @GET(CATEGORIES)
    suspend fun getCategories(): CategoryResponse
}