package com.example.mangaloo.database

import com.example.mangaloo.model.db.Manga
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDAO: AppDAO
) {
    suspend fun insertManga(manga: Manga){
        return appDAO.insertOne(manga)
    }
    suspend fun getManga(mangaId:String):Manga?{
        return appDAO.getManga(mangaId)
    }
    suspend fun deleteManga(mangaId: String){
        return appDAO.deleteManga(mangaId)
    }

    suspend fun getAllMangas():List<Manga>{
        return appDAO.getAllMangas()
    }
}