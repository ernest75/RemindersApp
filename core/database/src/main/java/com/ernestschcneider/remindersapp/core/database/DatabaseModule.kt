package com.ernestschcneider.remindersapp.core.database

import android.content.Context
import androidx.room.Room
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

object DatabaseModule {

    private const val DATABASE_NAME = "reminder_db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room
            .databaseBuilder(
                context = appContext,
                klass = AppDatabase::class.java,
                name = DATABASE_NAME
            ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): NoteDao {
        return database.userDao()
    }
}
