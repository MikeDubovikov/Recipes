package com.mdubovikov.recipes.data.remote.service

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
    suspend fun searchMealsByName(@Query(SEARCH_BY_NAME_QUERY) name: String): MealResponse

    @GET(MEALS)
    suspend fun searchMealsByArea(@Query(SEARCH_BY_AREA_QUERY) area: String): MealResponse

    @GET(SEARCH_MEALS)
    suspend fun searchMealsByFirstLetter(@Query(SEARCH_BY_FIRST_LETTER_QUERY) firstLetter: String): MealResponse

    @GET(MEALS)
    suspend fun searchMealsByIngredient(@Query(SEARCH_BY_INGREDIENT_QUERY) ingredient: String): MealResponse

    @GET(CATEGORIES)
    suspend fun getCategories(): CategoryResponse

    private companion object {
        const val CATEGORIES = "categories.php"
        const val MEAL_DETAILS = "lookup.php"
        const val SEARCH_MEALS = "search.php"
        const val MEALS = "filter.php"

        const val MEALS_QUERY = "c"
        const val MEAL_DETAILS_QUERY = "i"
        const val SEARCH_BY_NAME_QUERY = "s"
        const val SEARCH_BY_FIRST_LETTER_QUERY = "f"
        const val SEARCH_BY_INGREDIENT_QUERY = "i"
        const val SEARCH_BY_AREA_QUERY = "a"
    }
}