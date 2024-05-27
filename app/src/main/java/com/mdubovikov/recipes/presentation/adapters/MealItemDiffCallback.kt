package com.mdubovikov.recipes.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mdubovikov.recipes.domain.model.MealModel

class MealItemDiffCallback : DiffUtil.ItemCallback<MealModel>() {
    override fun areItemsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealModel, newItem: MealModel): Boolean {
        return oldItem == newItem
    }
}