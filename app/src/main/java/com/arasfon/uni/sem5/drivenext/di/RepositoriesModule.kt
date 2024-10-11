package com.arasfon.uni.sem5.drivenext.di

import com.arasfon.uni.sem5.drivenext.data.repositories.DataStoreRepositoryImpl
import com.arasfon.uni.sem5.drivenext.domain.repositories.DataStoreRepository
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
