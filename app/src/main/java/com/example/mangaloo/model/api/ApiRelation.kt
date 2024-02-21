package com.example.mangaloo.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRelation(
    val result: String,
    val response: String,
    val attributes: ApiRelationAttributes
)
