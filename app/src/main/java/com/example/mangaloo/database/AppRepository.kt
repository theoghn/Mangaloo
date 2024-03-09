package com.example.mangaloo.database

import com.example.mangaloo.model.db.Manga
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDAO: AppDAO
) {
    suspend fun insertManga(manga: Manga){
        return appDAO.insertOne(manga)
    }
}