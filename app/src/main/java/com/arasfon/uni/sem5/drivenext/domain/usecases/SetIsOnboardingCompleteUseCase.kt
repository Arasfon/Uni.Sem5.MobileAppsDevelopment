package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.repositories.DataStoreRepository
import javax.inject.Inject

class SetIsOnboardingCompleteUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isOnboardingComplete: Boolean) {
        dataStoreRepository.setIsOnboardingComplete(isOnboardingComplete)
    }
}
