package com.fliker.bookshelf.presentation.detail

import com.fliker.bookshelf.domain.model.Book

data class BookDetailState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null,
    val isSaved: Boolean = false
)