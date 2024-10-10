package com.arasfon.uni.mobileappsdevelopment.data.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val isOnboardingComplete: Flow<Boolean>
    suspend fun setIsOnboardingComplete(isOnboardingComplete: Boolean)
}
