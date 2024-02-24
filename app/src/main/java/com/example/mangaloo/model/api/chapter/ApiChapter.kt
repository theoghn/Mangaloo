package com.example.mangaloo.model.api.chapter

import com.example.mangaloo.model.api.ApiRelation
import kotlinx.serialization.Serializable

@Serializable
data class ApiChapter(
    val id: String,
    val attributes: ApiChapterAttributes,
    val relationships: List<ApiRelation>
)
