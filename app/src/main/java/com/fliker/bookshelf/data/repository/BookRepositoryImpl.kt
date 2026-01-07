package com.fliker.bookshelf.data.repository

import com.fliker.bookshelf.data.local.BookDao
import com.fliker.bookshelf.data.local.entity.BookEntity
import com.fliker.bookshelf.data.remote.BooksApi
import com.fliker.bookshelf.data.remote.dto.BookDto
import com.fliker.bookshelf.domain.model.Book
import com.fliker.bookshelf.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BooksApi,
    private val dao: BookDao
) : BookRepository {

    //API
    override suspend fun searchBook(query: String): List<Book> {
        return try {
            api.searchBooks(query).items?.map { toBook(it) } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
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

    //LOCAL
    override suspend fun saveBook(book: Book) {
        dao.insertBook(book.toEntity())
    }

    override suspend fun deleteBook(id: String) {
        val entity = dao.getBookById(id)
        if (entity != null) {
            dao.deleteBook(entity)
        }
    }

    override suspend fun getSavedBook(id: String): Book? {
        return dao.getBookById(id)?.toDomain()
    }

    override fun getAllSavedBooks(): Flow<List<Book>> {
        return dao.getAllBooks().map { list ->
            list.map { it.toDomain() }
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
                ?.replace(
                    "&zoom=1",
                    "&zoom=0"
                ) // ZOOM 0 = High Quality временно убрал, т.к. иногда показывает image not аvailable
                ?.replace("&edge=curl", ""),
            description = dto.volumeInfo.description
                ?: "", // Тут часто приходит HTML, потом почистим
            pageCount = dto.volumeInfo.pageCount ?: 0,
            averageRating = dto.volumeInfo.averageRating ?: 0.0
        )
    }

    //Book -> Entity (For DB saving)
    private fun Book.toEntity(): BookEntity {
        return BookEntity(
            id = id,
            title = title,
            authors = authors,
            imageUrl = imageUrl,
            highResImageUrl = highResImageUrl,
            description = description,
            pageCount = pageCount,
            averageRating = averageRating
        )
    }

    // Entity -> Book (For reading from DB)
    private fun BookEntity.toDomain(): Book {
        return Book(
            id = id,
            title = title,
            authors = authors,
            imageUrl = imageUrl,
            highResImageUrl = highResImageUrl,
            description = description,
            pageCount = pageCount,
            averageRating = averageRating
        )
    }
}