package com.example.mangaloo.api

import com.example.mangaloo.model.api.ApiChapterResponse
import com.example.mangaloo.model.api.manga.ApiMangaResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.mangadex.org/"
val retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface MangaDexApiService {
//    get manga chapters without empty pages ordered by chapter
    @GET("manga/{mangaId}/feed?order%5Bchapter%5D=asc&includeEmptyPages=0&includes%5B%5D=scanlation_group")
    suspend fun getMangaChapters(@Path("mangaId")mangaId:String,@Query("translatedLanguage[]") languages: List<String>): Response<ApiChapterResponse>
    @GET("manga/{mangaId}?includes%5B%5D=cover_art&includes%5B%5D=author")
    suspend fun getManga(@Path("mangaId")mangaId:String): Response<ApiMangaResponse>
}

object MangaDexApi {
    val retrofitService : MangaDexApiService by lazy {
        retrofit.create(MangaDexApiService::class.java)
    }
}
