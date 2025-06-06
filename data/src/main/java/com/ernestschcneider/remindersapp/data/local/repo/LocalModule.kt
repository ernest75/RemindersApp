package com.ernestschcneider.remindersapp.data.local.repo

import com.ernestschcneider.remindersapp.data.local.datasource.ReminderLocalDataSource
import com.ernestschcneider.remindersapp.data.local.datasource.ReminderLocalDataSourceImpl
import com.ernestschcneider.remindersapp.local.LocalRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    @Singleton
    internal abstract fun providesLocalRepo(
        impl: LocalRepoImpl
    ): LocalRepo

    @Binds
    @Singleton
    internal abstract fun providesReminderLocalDataSource(
        impl: ReminderLocalDataSourceImpl
    ): ReminderLocalDataSource
}
