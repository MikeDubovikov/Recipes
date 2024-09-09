package com.mdubovikov.recipes.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mdubovikov.recipes.databinding.ItemCardBinding
import com.mdubovikov.recipes.domain.model.MealModel

class MealAdapter(
    private val onClickMeal: ((mealId: Int) -> Unit)?
) : ListAdapter<MealModel, MealItemViewHolder>(MealItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealItemViewHolder {

        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MealItemViewHolder(binding, onClickMeal)
    }

    override fun onBindViewHolder(holder: MealItemViewHolder, position: Int) {
        val meal = getItem(position)
        holder.bind(meal)
    }
}