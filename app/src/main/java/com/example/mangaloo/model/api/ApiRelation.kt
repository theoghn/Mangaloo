package com.example.mangaloo.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRelation(
    val id: String,
    val type: String,
    val attributes: ApiRelationAttributes
)
