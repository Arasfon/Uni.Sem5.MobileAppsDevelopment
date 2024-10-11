package com.arasfon.uni.sem5.drivenext.domain.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val isOnboardingComplete: Flow<Boolean>
    suspend fun setIsOnboardingComplete(isOnboardingComplete: Boolean)
}
