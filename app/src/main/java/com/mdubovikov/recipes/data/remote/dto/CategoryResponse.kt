package com.mdubovikov.recipes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>
)