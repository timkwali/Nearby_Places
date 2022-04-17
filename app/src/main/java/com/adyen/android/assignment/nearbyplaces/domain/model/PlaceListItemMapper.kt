package com.adyen.android.assignment.nearbyplaces.domain.model

import com.adyen.android.assignment.common.data.cache.model.Place
import com.adyen.android.assignment.common.utils.DomainMapper

class PlaceListItemMapper: DomainMapper<Place, PlaceListItem> {
    override suspend fun mapToDomain(entity: Place): PlaceListItem {
        return PlaceListItem(
            id = entity.id,
            distance = entity.distance,
            name = entity.name
        )
    }
}