package com.example.mangaloo.model

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class ChapterAttributes(
    val volume: String?,
    val chapter: String?,
    val translatedLanguage: String?,
    val pages: Int?,
)