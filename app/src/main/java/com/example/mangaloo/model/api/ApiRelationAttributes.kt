package com.example.mangaloo.model.api

import kotlinx.serialization.Serializable

@Serializable
data class ApiRelationAttributes(
    val name:String?,
    val website:String?,
    val fileName:String?

)
