package com.example.mangaloo.model

data class SortingCriteria(
    val field: String,
    val order: String // Assuming order can be "asc" or "desc"
)
