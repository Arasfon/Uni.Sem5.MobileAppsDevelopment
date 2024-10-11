package com.arasfon.uni.sem5.drivenext.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    val isConnected: Flow<Boolean>
}
