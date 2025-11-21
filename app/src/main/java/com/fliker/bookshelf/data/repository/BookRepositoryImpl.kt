package com.fliker.bookshelf.data.repository

import com.fliker.bookshelf.data.remote.BooksApi
import com.fliker.bookshelf.domain.model.Book
import com.fliker.bookshelf.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BooksApi
) : BookRepository {
    override suspend fun searchBook(query: String): List<Book> {
        val response = api.searchBooks(query)

        return response.items?.map { dto ->
            Book(
                id = dto.id,
                title = dto.volumeInfo.title ?: "Без названия",

                authors = dto.volumeInfo.authors?.joinToString(", ") ?: " ",
                imageUrl = dto.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
                description = dto.volumeInfo.description ?: " ",
                pageCount = dto.volumeInfo.pageCount ?: 0,
                averageRating = dto.volumeInfo.averageRating ?: 0.0
            )
        } ?: emptyList()
    }
}