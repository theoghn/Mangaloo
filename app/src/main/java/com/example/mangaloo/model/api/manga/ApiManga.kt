package com.example.mangaloo.model.api.manga

import com.example.mangaloo.model.api.ApiChapterAttributes
import com.example.mangaloo.model.api.ApiRelation
import kotlinx.serialization.Serializable

@Serializable
data class ApiManga(
    val id: String,
    val attributes: ApiMangaAttributes,
    val relationships:List<ApiRelation>
)
