package com.example.mangaloo.model.api

import com.example.mangaloo.model.Chapter
import kotlinx.serialization.Serializable

@Serializable
data class ApiChapterResponse(
    val result: String,
    val response: String,
    val data: List<Chapter>
)