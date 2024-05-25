package com.mdubovikov.recipes.data.remote.dto

import com.google.gson.annotations.SerializedName

class CategoryDto(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val image: String,
    @SerializedName("strCategoryDescription")
    val description: String
)