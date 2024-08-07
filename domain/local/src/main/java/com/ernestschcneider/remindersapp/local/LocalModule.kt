package com.ernestschcneider.remindersapp.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {
    @Binds
    @Singleton
    internal abstract fun providesLocalRepo(
        repository: LocalRepo
    ): StorageRepo
}
