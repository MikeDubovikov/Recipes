package com.mdubovikov.recipes.presentation.adapters

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mdubovikov.recipes.R
import com.mdubovikov.recipes.databinding.ItemCategoryBinding
import com.mdubovikov.recipes.domain.model.CategoryModel

class CategoryItemViewHolder(
    private val binding: ItemCategoryBinding,
    private val onClickCategoryHome: ((category: CategoryModel) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: CategoryModel) {

        with(binding) {
            with(category) {
                tvCategoryName.text = name

                Glide.with(ivCategory)
                    .load(Uri.parse(image))
                    .centerCrop()
                    .placeholder(R.drawable.ic_loading)
                    .into(ivCategory)

                root.setOnClickListener { onClickCategoryHome.invoke(category) }
            }
        }
    }
}