package com.example.mangaloo.model.api.manga


import kotlinx.serialization.Serializable


@Serializable
data class ApiMangaAttributes(
    val title: LanguageWithString,
    var lastChapter: String?,
    val status: String,
    val latestUploadedChapter:String?
)

@Serializable
data class LanguageWithString(
    val en:String
)
