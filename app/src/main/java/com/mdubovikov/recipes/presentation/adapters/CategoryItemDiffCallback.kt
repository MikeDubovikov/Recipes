package com.mdubovikov.recipes.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mdubovikov.recipes.domain.model.CategoryModel

class CategoryItemDiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem == newItem
    }
}