package com.example.mangaloo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mangaloo.model.db.Manga

@Dao
interface AppDAO {
    @Insert
    suspend fun insertOne(manga:Manga)

    @Query("SELECT * FROM manga WHERE id like :mangaId")
    suspend fun getManga(mangaId: String): Manga?

    @Query("DELETE FROM manga WHERE id = :mangaId")
    suspend fun deleteManga(mangaId:String)
}