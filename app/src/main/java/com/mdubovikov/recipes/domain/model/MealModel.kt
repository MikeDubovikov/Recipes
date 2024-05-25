package com.mdubovikov.recipes.domain.model

data class MealModel(
    val id: String = "-1",
    val name: String = "null",
    val image: String? = "null",
    val isSaved: Boolean = false
)