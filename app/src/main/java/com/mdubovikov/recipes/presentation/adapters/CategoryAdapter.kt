package com.mdubovikov.recipes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mdubovikov.recipes.databinding.ItemCategoryBinding
import com.mdubovikov.recipes.domain.model.CategoryModel

class CategoryAdapter(
    private val onClickCategory: ((category: CategoryModel) -> Unit)
) : ListAdapter<CategoryModel, CategoryItemViewHolder>(CategoryItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {

        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CategoryItemViewHolder(binding, onClickCategory)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

}