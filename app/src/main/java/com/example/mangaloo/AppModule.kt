package com.example.mangaloo

import android.content.Context
import androidx.room.Room
import com.example.mangaloo.database.AppDAO
import com.example.mangaloo.database.AppDatabase
import com.example.mangaloo.database.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(app,AppDatabase::class.java,"MangalooDB").build()

    @Singleton
    @Provides
    fun provideAppDAO(db:AppDatabase) = db.getDao()

    @Provides
    @Singleton
    fun provideAppRepository(appDao:AppDAO): AppRepository {
        return AppRepository(appDao)
    }

}