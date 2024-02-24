package com.example.mangaloo.model.api.manga

data class ApiMangaStatsResponse(
    val result: String,
    val statistics: Map<String, Statistics>
)

data class Statistics(
    val rating: Rating,
    val follows: Number
)

data class Rating(
    val average: Number,
    val bayesian: Number,
)