package com.example.mangaloo.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiChapter(
    val id: String,
    val attributes: ApiChapterAttributes,
    val relationships: List<ApiRelation>
)
