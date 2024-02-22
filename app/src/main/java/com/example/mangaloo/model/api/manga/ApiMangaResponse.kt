package com.example.mangaloo.model.api.manga


import kotlinx.serialization.Serializable

@Serializable
data class ApiMangaResponse(
    val result: String,
    val response: String,
    val data: List<ApiManga?>
)
