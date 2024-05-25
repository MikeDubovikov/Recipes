package com.mdubovikov.recipes.common

object Constants {

    const val DATABASE_NAME = "meal.db"

    const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    object Endpoints {
        const val CATEGORIES = "categories.php"
        const val MEAL_DETAILS = "lookup.php"
        const val SEARCH_MEALS = "search.php"
        const val MEALS = "filter.php"
    }

    object Queries{
        const val MEALS_QUERY = "c"
        const val MEAL_DETAILS_QUERY = "i"
        const val SEARCH_QUERY = "s"
    }
}