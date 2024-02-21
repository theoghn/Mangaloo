package com.example.mangaloo.api

import com.example.mangaloo.model.ChapterResponse
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
    @GET("manga/{mangaId}/feed?order%5Bchapter%5D=asc&includeEmptyPages=0")
    suspend fun getPhotos(@Path("mangaId")mangaId:String,@Query("translatedLanguage[]") languages: List<String>): Response<ChapterResponse>
}

object MangaDexApi {
    val retrofitService : MangaDexApiService by lazy {
        retrofit.create(MangaDexApiService::class.java)
    }
}
