package com.mdubovikov.recipes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_meals")
data class MealEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val image: String?,
    val isSaved: Boolean
)