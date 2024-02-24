package com.example.mangaloo.model.api.manga


import com.example.mangaloo.model.api.TextInLanguage
import kotlinx.serialization.Serializable


@Serializable
data class ApiMangaAttributes(
    val title: TextInLanguage,
    val description:TextInLanguage,
    var lastChapter: String?,
    val status: String,
    val latestUploadedChapter:String?,
    val externalUrl:String?
)


