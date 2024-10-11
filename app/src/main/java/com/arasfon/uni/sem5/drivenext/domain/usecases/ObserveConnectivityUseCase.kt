package com.arasfon.uni.sem5.drivenext.domain.usecases

import com.arasfon.uni.sem5.drivenext.domain.repositories.ConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectivityUseCase @Inject constructor(
    private val connectivityRepository: ConnectivityRepository
) {
    operator fun invoke(): Flow<Boolean> = connectivityRepository.isConnected
}
