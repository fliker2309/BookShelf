package com.fliker.bookshelf.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fliker.bookshelf.presentation.components.BookItem

@Composable
fun HomeScreen(
    onBookClick : (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel() // Магия Hilt создает ViewModel сама
) {
    val state = viewModel.state.value
    var text by remember { mutableStateOf("Harry Potter") } // Текст поиска по умолчанию

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // 1. Поле поиска
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                label = { Text("Поиск книг") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { viewModel.searchBooks(text) }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Найти")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Контент (Загрузка, Ошибка или Список)
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.books) { book ->
                        BookItem(
                            title = book.title,
                            authors = book.authors,
                            imageUrl = book.imageUrl,
                            rating = book.averageRating,
                            onClick = { onBookClick(book.id) }
                        )
                    }
                }
            }
        }
    }
}