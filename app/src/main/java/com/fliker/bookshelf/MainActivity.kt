package com.fliker.bookshelf

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.fliker.bookshelf.data.remote.BooksApi
import com.fliker.bookshelf.ui.theme.BookShelfTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint // 1. Эта метка обязательна, чтобы Hilt мог сюда что-то внедрять
class MainActivity : ComponentActivity() {

    // 2. Мы говорим: "Hilt, дай мне сюда готовый экземпляр API".
    // Нам не нужно писать = Retrofit.Builder()..., мы это уже сделали в Module.
    @Inject
    lateinit var api: BooksApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3. Запускаем корутину (поток), потому что сеть нельзя трогать в главном потоке
        lifecycleScope.launch {
            try {
                Log.d("BOOK_TEST", "🚀 Начинаем запрос к Google...")

                // 4. Вызываем наш метод поиска
                val response = api.searchBooks("Harry Potter")

                // 5. Если книги пришли, перебираем их
                response.items?.forEach { book ->
                    Log.d("BOOK_TEST", "📚 Книга: ${book.volumeInfo.title}")
                }

                Log.d("BOOK_TEST", "✅ Запрос завершен успешно!")

            } catch (e: Exception) {
                // 6. Если что-то сломалось (нет инета, ошибка сервера)
                Log.e("BOOK_TEST", "❌ Ошибка: ${e.message}")
                e.printStackTrace()
            }
        }

        setContent {
            BookShelfTheme() {
                // Пока оставляем пустой экран
            }
        }
    }
}