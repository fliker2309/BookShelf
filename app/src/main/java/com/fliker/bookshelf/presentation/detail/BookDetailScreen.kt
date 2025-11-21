package com.fliker.bookshelf.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState() // Для прокрутки экрана

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Индикатор загрузки
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        // 2. Сообщение об ошибке
        else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // 3. Контент книги (если она загружена)
        else if (state.book != null) {
            val book = state.book

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState) // Включаем скролл
            ) {
                // --- Обложка ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = book.highResImageUrl, // 1. Цель: Грузим HD
                        contentDescription = null,

                        placeholder = rememberAsyncImagePainter(model = book.imageUrl),
                        error = rememberAsyncImagePainter(model = book.imageUrl),

                        modifier = Modifier
                            .height(280.dp)
                            .width(180.dp)
                            .shadow(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // --- Информация ---
                Column(modifier = Modifier.padding(16.dp)) {
                    // Название
                    Text(
                        text = book.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Автор
                    Text(
                        text = book.authors,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Метрики (Рейтинг и Страницы)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Звездочка
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107) // Золотой цвет
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${book.averageRating}",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "${book.pageCount} стр.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Заголовок "Описание"
                    Text(
                        text = "О книге",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Само описание (чистим от HTML тегов)
                    val cleanDescription = book.description
                        .replace(Regex("<.*?>"), "") // Удаляет <p>, <br> и прочее

                    Text(
                        text = cleanDescription.ifBlank { "Описание отсутствует" },
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}