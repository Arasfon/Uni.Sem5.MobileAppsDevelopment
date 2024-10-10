package com.arasfon.uni.sem5.drivenext.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.arasfon.uni.sem5.drivenext.data.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override val isOnboardingComplete: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DataStoreKeys.IS_ONBOARDING_COMPLETE] ?: false
        }

    override suspend fun setIsOnboardingComplete(isOnboardingComplete: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_ONBOARDING_COMPLETE] = isOnboardingComplete
        }
    }
}
