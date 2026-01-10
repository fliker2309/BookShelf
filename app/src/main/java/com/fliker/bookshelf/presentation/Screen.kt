package com.fliker.bookshelf.presentation

sealed class Screen(val route: String) {

    data object Home : Screen("home_screen")

    data object Detail : Screen("detail_screen/{bookId}") {
        fun createRoute(bookId: String) = "detail_screen/$bookId"
    }
}