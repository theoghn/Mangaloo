package com.example.mangaloo.model.api



data class Chapter(
    val hash: String,
    val dataSaver: List<String>,
    val data: List<String>

)

data class ApiImagesResponse(
    val result: String,
    val baseUrl: String,
    val chapter: Chapter
)