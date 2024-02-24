package com.example.mangaloo.model.api.manga

import com.example.mangaloo.model.api.ApiRelation
import kotlinx.serialization.Serializable


@Serializable
data class ApiSingleMangaResponse(
    val result: String,
    val response: String,
    val data: ApiManga?
)
