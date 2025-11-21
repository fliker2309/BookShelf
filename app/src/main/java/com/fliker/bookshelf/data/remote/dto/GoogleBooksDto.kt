package com.fliker.bookshelf.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BooksResponseDto(
    @SerializedName("items")
    val items: List<BookDto>?
)

data class BookDto(
    val id: String,
    val volumeInfo: VolumeInfoDto
)

data class VolumeInfoDto(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val pageCount: Int?,
    val averageRating: Double?,
    val imageLinks: ImageLinksDto?
)

data class ImageLinksDto(
    val smallThumbnail: String?,
    val thumbnail: String?
)