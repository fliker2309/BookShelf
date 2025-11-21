package com.fliker.bookshelf.domain.repository

import com.fliker.bookshelf.domain.model.Book

interface BookRepository {
    suspend fun searchBook(query: String): List<Book>
    suspend fun getBookDetails(bookId: String): Book?


}