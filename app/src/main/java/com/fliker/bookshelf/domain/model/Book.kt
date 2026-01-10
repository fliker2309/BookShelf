package com.fliker.bookshelf.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: String,
    val imageUrl: String?,
    val highResImageUrl: String?,
    val description: String,
    val pageCount: Int,
    val averageRating: Double
)

