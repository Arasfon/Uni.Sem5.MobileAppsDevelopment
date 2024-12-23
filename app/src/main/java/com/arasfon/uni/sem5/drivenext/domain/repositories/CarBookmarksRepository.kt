package com.arasfon.uni.sem5.drivenext.domain.repositories

import com.arasfon.uni.sem5.drivenext.domain.models.cars.CarBrief
import kotlinx.coroutines.flow.Flow

interface CarBookmarksRepository {
    suspend fun getAll(): Flow<CarBrief>
    suspend fun add(id: Int)
}
