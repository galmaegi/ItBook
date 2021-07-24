package com.example.itbook.di

import android.content.Context
import com.example.itbookapi.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideSearchHistoryDao(appDatabase: AppDatabase) = appDatabase.getSearchHistoryDao()

    @Provides
    fun provideBookmarkDao(appDatabase: AppDatabase) = appDatabase.getBookmarkDao()
}