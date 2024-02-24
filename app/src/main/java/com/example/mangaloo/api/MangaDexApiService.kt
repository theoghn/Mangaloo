package com.example.mangaloo.api

import com.example.mangaloo.model.api.ApiChapterResponse
import com.example.mangaloo.model.api.manga.ApiMangaResponse
import com.example.mangaloo.model.api.manga.ApiMangaStatsResponse
import com.example.mangaloo.model.api.manga.ApiSingleMangaResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.mangadex.org/"
val retrofit: Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface MangaDexApiService {
    //    get manga chapters without empty pages ordered by chapter
    @GET("manga/{mangaId}/feed?order%5Bchapter%5D=asc&includeEmptyPages=0&includes%5B%5D=scanlation_group")
    suspend fun getMangaChapters(
        @Path("mangaId") mangaId: String,
        @Query("limit") limit: Int,
        @Query("translatedLanguage[]") languages: List<String>
    ): Response<ApiChapterResponse>

    @GET("manga?&order%5Brating%5D=desc&order%5BfollowedCount%5D=desc")
    suspend fun getManga(
        @Query("title") title: String,
        @Query("includes[]") relations: List<String>,
        @Query("limit") limit: Int,
        @Query("contentRating[]") content: List<String>
    ): Response<ApiMangaResponse>

    @GET("manga/{mangaId}")
    suspend fun getSingleManga(
        @Path("mangaId") mangaId: String,
        @Query("includes[]") relations: List<String>
    ): Response<ApiSingleMangaResponse>

    @GET("/statistics/manga/{mangaId}")
    suspend fun getMangaStats(@Path("mangaId") mangaId: String, ): Response<ApiMangaStatsResponse>
    @GET("chapter")
    suspend fun getChapter(@Query("ids[]") id: List<String?>): Response<ApiChapterResponse>
}

object MangaDexApi {
    val retrofitService: MangaDexApiService by lazy {
        retrofit.create(MangaDexApiService::class.java)
    }
}
