package com.mdubovikov.recipes.presentation.adapters

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.databinding.ItemCardBinding
import com.mdubovikov.recipes.domain.model.CategoryModel

class CategoryItemViewHolder(
    private val binding: ItemCardBinding,
    private val onClickCategoryHome: ((category: CategoryModel) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: CategoryModel) {

        with(binding) {
            with(category) {
                tvMealName.text = name

                Glide.with(ivMeal)
                    .load(Uri.parse(image))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .into(ivMeal)

                root.setOnClickListener { onClickCategoryHome.invoke(category) }
            }
        }
    }
}