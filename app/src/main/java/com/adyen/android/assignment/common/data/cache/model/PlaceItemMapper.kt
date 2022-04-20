package com.adyen.android.assignment.common.data.cache.model

import com.adyen.android.assignment.common.data.api.model.Main
import com.adyen.android.assignment.common.data.api.model.Result
import com.adyen.android.assignment.common.utils.DomainMapper

class PlaceItemMapper: DomainMapper<Result, Place> {
    override suspend fun mapToDomain(entity: Result): Place {
        val address: String = entity.location.run {
            "${this?.address ?: ""} ${this?.postcode ?: ""} ${this?.locality ?: ""} ${this?.region ?: ""}"
        }

        return Place(
            name = entity.name,
            distance = entity.distance,
            address = address.trim(),
            timeZone = entity.timezone,
            coordinates = entity.geocodes?.main ?: Main(0.0, 0.0),
            categories = entity.categories
        )
    }
}