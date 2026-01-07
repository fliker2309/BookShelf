package com.fliker.bookshelf.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val authors: String,
    val imageUrl: String?,
    val highResImageUrl: String?,
    val description: String,
    val pageCount: Int,
    val averageRating: Double,
    val addedAt: Long = System.currentTimeMillis()
)
