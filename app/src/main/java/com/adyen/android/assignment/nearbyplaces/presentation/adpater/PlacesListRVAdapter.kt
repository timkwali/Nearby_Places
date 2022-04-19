package com.adyen.android.assignment.nearbyplaces.presentation.adpater

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.OnItemClick
import com.adyen.android.assignment.nearbyplaces.domain.model.PlaceListItem
import java.util.*

class PlacesListRVAdapter(
    private val listener: OnItemClick<Place>
): ListAdapter<Place, PlacesListViewHolder>(REPO_COMPARATOR) {
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesListViewHolder {
        return PlacesListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlacesListViewHolder, position: Int) {
        val currentPlace = getItem(position)
        holder.bind(currentPlace, listener)
    }
}