package com.mdubovikov.recipes.common

object Constants {

    const val DATABASE_NAME = "meal.db"

    const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    const val DATA_STORE_NAME = "SETTINGS"

    object Endpoints {
        const val CATEGORIES = "categories.php"
        const val MEAL_DETAILS = "lookup.php"
        const val SEARCH_MEALS = "search.php"
        const val MEALS = "filter.php"
    }

    object Queries {
        const val CATEGORY = "CATEGORY"
        const val SEARCH_BY_NAME = "SEARCH_BY_NAME"
        const val SEARCH_BY_FIRST_LETTER = "SEARCH_BY_FIRST_LETTER"
        const val SEARCH_BY_INGREDIENT = "SEARCH_BY_INGREDIENT"
        const val SEARCH_BY_AREA = "SEARCH_BY_AREA"
        const val MEALS_QUERY = "c"
        const val MEAL_DETAILS_QUERY = "i"
        const val SEARCH_BY_NAME_QUERY = "s"
        const val SEARCH_BY_FIRST_LETTER_QUERY = "f"
        const val SEARCH_BY_INGREDIENT_QUERY = "i"
        const val SEARCH_BY_AREA_QUERY = "a"
    }
}