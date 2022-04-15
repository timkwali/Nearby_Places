package com.adyen.android.assignment.common.utils

interface DomainMapper<DomainModel, Dto> {

    suspend fun mapToDomain(entity: DomainModel): Dto
}