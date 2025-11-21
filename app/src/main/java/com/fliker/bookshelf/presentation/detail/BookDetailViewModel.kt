package com.fliker.bookshelf.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fliker.bookshelf.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle // <- Это магия Android. Тут лежат аргументы навигации
) : ViewModel() {

    private val _state = mutableStateOf(BookDetailState())
    val state: State<BookDetailState> = _state

    init {
        // Как только ViewModel создается, мы достаем "bookId" из аргументов
        // "bookId" должен совпадать с тем, что мы писали в Screen.Detail: "detail_screen/{bookId}"
        val bookId = savedStateHandle.get<String>("bookId")

        if (bookId != null) {
            loadBook(bookId)
        }
    }

    private fun loadBook(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val book = repository.getBookDetails(id)

            if (book != null) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    book = book,
                    error = null
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Не удалось загрузить книгу"
                )
            }
        }
    }
}