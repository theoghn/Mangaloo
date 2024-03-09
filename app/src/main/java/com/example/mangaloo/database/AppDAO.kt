package com.example.mangaloo.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.mangaloo.model.db.Manga

@Dao
interface AppDAO {
    @Insert
    suspend fun insertOne(manga:Manga)
}