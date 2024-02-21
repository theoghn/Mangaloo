package com.example.mangaloo.model

import com.example.mangaloo.model.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class ChapterResponse(
    val result: String,
    val response: String,
    val data: List<Chapter>
)