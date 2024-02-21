package com.example.mangaloo.model.api.manga


import kotlinx.serialization.Serializable


@Serializable
data class ApiMangaAttributes(
    val title: LanguageWithString,
    val lastChapter: String?,
    val status: String
)

@Serializable
data class LanguageWithString(
    val en:String
)
