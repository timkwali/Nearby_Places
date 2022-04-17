package com.adyen.android.assignment.nearbyplaces.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.R
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.OnItemClick
import com.adyen.android.assignment.databinding.PlaceRvItemBinding
import com.adyen.android.assignment.nearbyplaces.domain.model.PlaceListItem

class PlacesListViewHolder(private val binding: PlaceRvItemBinding):
    RecyclerView.ViewHolder(binding.root) {

    fun bind(placeListItem: Place, action: OnItemClick<Place>) {
        binding.apply {
            placeNameTv.text = "${placeListItem.distance} ${itemView.context.getString(R.string.km_away)}"
            distanceTv.text = placeListItem.name
        }
        itemView.setOnClickListener {
            action.onItemClick(placeListItem, adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PlacesListViewHolder {
            val binding = PlaceRvItemBinding.inflate(
                LayoutInflater
                .from(parent.context), parent, false)
            return PlacesListViewHolder(binding)
        }
    }
}