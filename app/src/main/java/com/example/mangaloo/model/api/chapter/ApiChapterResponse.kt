package com.example.mangaloo.model.api.chapter


import com.example.mangaloo.model.api.chapter.ApiChapter
import kotlinx.serialization.Serializable

@Serializable
data class ApiChapterResponse(
    val result: String,
    val response: String,
    val data: List<ApiChapter>
)