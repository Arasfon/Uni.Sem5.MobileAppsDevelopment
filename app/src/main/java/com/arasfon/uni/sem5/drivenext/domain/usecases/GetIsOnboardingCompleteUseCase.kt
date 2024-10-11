package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsOnboardingCompleteUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> = dataStoreRepository.isOnboardingComplete
}
