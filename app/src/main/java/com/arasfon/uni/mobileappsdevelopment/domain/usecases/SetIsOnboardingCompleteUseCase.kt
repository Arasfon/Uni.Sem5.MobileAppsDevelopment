package com.arasfon.uni.mobileappsdevelopment.domain.usecases

import com.arasfon.uni.mobileappsdevelopment.data.repositories.DataStoreRepository
import javax.inject.Inject

class SetIsOnboardingCompleteUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isOnboardingComplete: Boolean) {
        dataStoreRepository.setIsOnboardingComplete(isOnboardingComplete)
    }
}
