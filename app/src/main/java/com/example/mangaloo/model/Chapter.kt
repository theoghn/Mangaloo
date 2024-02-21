package com.example.mangaloo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "chapter")
data class Chapter(
    @PrimaryKey
    val id: String,
    val type: String,
//    val attributes: ChapterAttributes,
)

