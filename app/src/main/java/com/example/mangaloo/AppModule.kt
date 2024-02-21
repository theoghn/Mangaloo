package com.example.mangaloo

import android.content.Context
import androidx.room.Room
import com.example.mangaloo.database.AppDatabase
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

}