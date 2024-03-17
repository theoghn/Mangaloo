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
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Manga

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}