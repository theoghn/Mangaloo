package com.example.mangaloo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangaloo.model.Chapter

@Database(
    entities = [Chapter::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getDao(): AppDAO
}