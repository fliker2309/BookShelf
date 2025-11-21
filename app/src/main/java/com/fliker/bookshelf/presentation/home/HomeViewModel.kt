package com.fliker.bookshelf.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.fliker.bookshelf.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    //screen state (UI state)
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    //find books
    fun searchBooks(query: String) {

        if (query.isBlank()) return

        viewModelScope.launch {
            //show download
            _state.value = state.value.copy(
                isLoading = true,
                error = ""
            )

            try {
                //going to internet through repository

                val result = repository.searchBook(query)
                _state.value = state.value.copy(
                    isLoading = false,
                    books = result
                )
            } catch (e: Exception) {
                //if error - save error text
                _state.value = state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}