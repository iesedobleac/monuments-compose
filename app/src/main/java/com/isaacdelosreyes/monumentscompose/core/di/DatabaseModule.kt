package com.isaacdelosreyes.monumentscompose.core.di

import android.content.Context
import androidx.room.Room
import com.isaacdelosreyes.monumentscompose.core.data.local.room.MonumentsDao
import com.isaacdelosreyes.monumentscompose.core.data.local.room.MonumentsDatabase
import com.isaacdelosreyes.monumentscompose.core.data.remote.retrofit.MonumentWs
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepository
import com.isaacdelosreyes.monumentscompose.core.data.repository.MonumentsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideMonumentsDao(database: MonumentsDatabase): MonumentsDao {
        return database.getMonumentsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): MonumentsDatabase {
        return Room.databaseBuilder(
            appContext,
            MonumentsDatabase::class.java,
            "monuments_database"
        ).build()
    }

    @Provides
    fun providesMonumentsRepository(
        monumentsDao: MonumentsDao,
        monumentWs: MonumentWs
    ): MonumentsRepository =
        MonumentsRepositoryImpl(monumentsDao, monumentWs)
}