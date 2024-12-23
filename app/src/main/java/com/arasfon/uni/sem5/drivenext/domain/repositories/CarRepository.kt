package com.arasfon.uni.sem5.drivenext.domain.repositories

import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarBrief
import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarDetails
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    suspend fun get(id: Int): CarBrief
    suspend fun getDetails(id: Int): CarDetails
    suspend fun searchByTextQuery(query: String): Flow<CarBrief>
}
