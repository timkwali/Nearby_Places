package com.adyen.android.assignment.placedetails.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.common.data.api.model.Category

class CategoryRvAdapter(var categoryList: List<Category>):
    RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categoryList[position]
        return holder.bind(currentCategory)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}