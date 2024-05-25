package com.mdubovikov.recipes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealDetailResponse(
    @SerializedName("meals")
    val meals: List<MealDetailDto>
)