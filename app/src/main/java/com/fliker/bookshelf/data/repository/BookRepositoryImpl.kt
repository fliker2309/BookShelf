package com.fliker.bookshelf.data.repository

import com.fliker.bookshelf.data.remote.BooksApi
import com.fliker.bookshelf.data.remote.dto.BookDto
import com.fliker.bookshelf.domain.model.Book
import com.fliker.bookshelf.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BooksApi
) : BookRepository {
    override suspend fun searchBook(query: String): List<Book> {
        val response = api.searchBooks(query)

        return response.items?.map { dto ->
            toBook(dto)
        } ?: emptyList()
    }

    override suspend fun getBookDetails(bookId: String): Book? {
        return try {
            val dto = api.getBookDetails(bookId)
            toBook(dto)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun toBook(dto: BookDto): Book {
        return Book(
            id = dto.id,
            title = dto.volumeInfo.title,
            authors = dto.volumeInfo.authors?.joinToString(", ") ?: "",
            imageUrl = dto.volumeInfo.imageLinks?.thumbnail
                ?.replace("http:", "https:"),
            highResImageUrl = dto.volumeInfo.imageLinks?.thumbnail
                ?.replace("http:", "https:")
                ?.replace("&zoom=1", "&zoom=0") // ZOOM 0 = High Quality временно убрал, т.к. иногда показывает image not аvailable
                ?.replace("&edge=curl", ""),
            description = dto.volumeInfo.description
                ?: "", // Тут часто приходит HTML, потом почистим
            pageCount = dto.volumeInfo.pageCount ?: 0,
            averageRating = dto.volumeInfo.averageRating ?: 0.0
        )
    }
}