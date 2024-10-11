package com.arasfon.uni.sem5.drivenext.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.arasfon.uni.sem5.drivenext.domain.repositories.ConnectivityRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConnectivityRepositoryImpl(
    private val context: Context
) : ConnectivityRepository {
    override val isConnected: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true).isSuccess
            }

            override fun onLost(network: Network) {
                trySend(false).isSuccess
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        // Initial state
        val initialConnected = connectivityManager.activeNetwork != null &&
                connectivityManager
                    .getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        trySend(initialConnected).isSuccess

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
}
