package com.arasfon.uni.mobileappsdevelopment.di

import com.arasfon.uni.mobileappsdevelopment.data.repositories.DataStoreRepository
import com.arasfon.uni.mobileappsdevelopment.data.repositories.DataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}
