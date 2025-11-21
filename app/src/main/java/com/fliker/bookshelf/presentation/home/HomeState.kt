package com.fliker.bookshelf.presentation.home

import com.fliker.bookshelf.domain.model.Book

data class HomeState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = ""
)
