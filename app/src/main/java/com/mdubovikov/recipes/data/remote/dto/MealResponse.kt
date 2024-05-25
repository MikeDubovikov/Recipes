package com.mdubovikov.recipes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("meals")
    val meals: List<MealDto>
)