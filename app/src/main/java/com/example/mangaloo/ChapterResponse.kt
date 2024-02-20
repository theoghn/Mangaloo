package com.example.mangaloo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChapterResponse(
    val result: String,
    val response: String,
    val data: List<Chapter>
)