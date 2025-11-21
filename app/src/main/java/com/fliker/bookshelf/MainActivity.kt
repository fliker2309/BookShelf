package com.fliker.bookshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fliker.bookshelf.presentation.Screen
import com.fliker.bookshelf.presentation.detail.BookDetailScreen
import com.fliker.bookshelf.presentation.home.HomeScreen
import com.fliker.bookshelf.ui.theme.BookShelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookShelfTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. Создаем контроллер навигации
                    val navController = rememberNavController()

                    // 2. Настраиваем "хост" (карту переходов)
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route // Стартовый экран
                    ) {
                        // Экран 1: Список книг
                        composable(route = Screen.Home.route) {
                            HomeScreen(
                                onBookClick = { bookId ->
                                    // Переходим на детали
                                    navController.navigate(Screen.Detail.createRoute(bookId))
                                }
                            )
                        }

                        // Экран 2: Детали
                        composable(route = Screen.Detail.route) { backStackEntry ->
                            // Достаем ID из аргументов навигации
                            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                            BookDetailScreen(bookId = bookId)
                        }
                    }
                }
            }
        }
    }
}