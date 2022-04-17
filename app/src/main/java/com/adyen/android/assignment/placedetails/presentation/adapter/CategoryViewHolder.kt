package com.adyen.android.assignment.placedetails.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.common.data.api.model.Category
import com.adyen.android.assignment.common.utils.Utils.loadImage
import com.adyen.android.assignment.databinding.CategoriesRvItemBinding

class CategoryViewHolder(private val binding: CategoriesRvItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        binding.apply {
            val imageUrl = "${category.icon?.prefix}${category.icon?.suffix}"
            categorySiv.loadImage(imageUrl)
            categoryTv.text = category.name
        }
    }

    companion object {
        fun create(parent: ViewGroup): CategoryViewHolder {
            val binding = CategoriesRvItemBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false)
            return CategoryViewHolder(binding)
        }
    }
}