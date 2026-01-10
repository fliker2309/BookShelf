package com.fliker.bookshelf.data.remote

import com.fliker.bookshelf.data.remote.dto.BookDto
import com.fliker.bookshelf.data.remote.dto.BooksResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String, //user's input
        @Query("maxResults") maxResults: Int = 20,
        @Query("langRestrict") lang: String = "ru" //rus language
    ) : BooksResponseDto

    @GET("volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") id: String
    ) : BookDto

    companion object{
        const val BASE_URL="https://www.googleapis.com/books/v1/"
    }
}