package com.example.mangaloo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangaloo.model.db.Manga

@Database(
    entities = [Manga::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {
    abstract fun getDao(): AppDAO
}