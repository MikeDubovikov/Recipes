package com.mdubovikov.recipes.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mdubovikov.recipes.R

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_loading)
        .into(view)
}