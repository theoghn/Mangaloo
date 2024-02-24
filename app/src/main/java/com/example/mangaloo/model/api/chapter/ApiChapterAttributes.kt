package com.example.mangaloo.model.api.chapter

import kotlinx.serialization.Serializable

@Serializable
data class ApiChapterAttributes(
    val volume: String?,
    val chapter: String?,
    val translatedLanguage: String?,
    val pages: Int?,
    val title: String,
    val createdAt:String
)
