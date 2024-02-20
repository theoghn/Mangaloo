package com.example.mangaloo

import kotlinx.serialization.Serializable

@Serializable
data class Chapter(
    val id: String,
    val type: String,
//    val attributes: ChapterAttributes,
)

