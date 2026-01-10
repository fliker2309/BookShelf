package com.fliker.bookshelf.domain.repository

import com.fliker.bookshelf.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBook(query: String): List<Book>
    suspend fun getBookDetails(bookId: String): Book?

    suspend fun saveBook(book: Book)
    suspend fun deleteBook(id: String)
    suspend fun getSavedBook(id: String): Book?
    fun getAllSavedBooks(): Flow<List<Book>>
}

