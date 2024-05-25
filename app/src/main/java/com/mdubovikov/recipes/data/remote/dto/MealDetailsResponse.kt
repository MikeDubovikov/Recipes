package com.mdubovikov.recipes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealDetailsResponse(
    @SerializedName("meals")
    val meals: List<MealDetailsDto>
)