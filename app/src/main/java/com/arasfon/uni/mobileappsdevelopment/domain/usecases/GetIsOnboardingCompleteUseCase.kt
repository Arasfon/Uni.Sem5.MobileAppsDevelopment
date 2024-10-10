package com.arasfon.uni.mobileappsdevelopment.domain.usecases

import com.arasfon.uni.mobileappsdevelopment.data.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsOnboardingCompleteUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> = dataStoreRepository.isOnboardingComplete
}
