package com.mdubovikov.recipes.presentation.adapters

import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.databinding.ItemMealBinding
import com.mdubovikov.recipes.domain.model.MealModel

class MealItemViewHolder(
    private val binding: ItemMealBinding,
    private val onClickMeal: ((mealId: Int) -> Unit)?,
    private val onSavedIconClicked: (MealModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(meal: MealModel) {

        with(binding) {
            with(meal) {
                tvMealName.text = name

                if (meal.isSaved) {
                    ivButton.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            ivButton.resources,
                            R.drawable.ic_heart_filled,
                            null
                        )
                    )
                } else {
                    ivButton.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            ivButton.resources,
                            R.drawable.ic_heart_linear,
                            null
                        )
                    )
                }

                Glide.with(ivMeal)
                    .load(Uri.parse(image))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .into(ivMeal)
                root.setOnClickListener { onClickMeal?.let { element -> element(meal.id.toInt()) } }
            }

            cvButtonImage.setOnClickListener { onSavedIconClicked.invoke(meal) }
        }
    }
}