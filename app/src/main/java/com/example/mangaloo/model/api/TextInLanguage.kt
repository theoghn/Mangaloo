package com.example.mangaloo.model.api

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TextInLanguage(
    val en:String,
    @SerializedName("ja-ro") val jaRo: String
)