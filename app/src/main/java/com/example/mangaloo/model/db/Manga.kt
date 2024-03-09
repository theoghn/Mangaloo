package com.example.mangaloo.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga")
class Manga(
    @PrimaryKey
    val id: String,
    val title: String?,
    val description: String?,
    var lastChapter: String?,
    val status: String?,
    val coverUrl:String?,
    val rating: Double,
    val follows: Double,
    val author :String?
)