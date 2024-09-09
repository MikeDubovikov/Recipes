package com.mdubovikov.recipes.presentation.adapters

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.databinding.ItemCardBinding
import com.mdubovikov.recipes.domain.model.MealModel

class MealItemViewHolder(
    private val binding: ItemCardBinding,
    private val onClickMeal: ((mealId: Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(meal: MealModel) {

        with(binding) {
            with(meal) {
                tvMealName.text = name

                Glide.with(ivMeal)
                    .load(Uri.parse(image))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .into(ivMeal)
                root.setOnClickListener { onClickMeal?.let { element -> element(meal.id.toInt()) } }
            }
        }
    }
}