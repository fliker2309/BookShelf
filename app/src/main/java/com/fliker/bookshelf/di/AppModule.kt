package com.fliker.bookshelf.di

import android.app.Application
import androidx.room.Room
import com.fliker.bookshelf.data.local.BookDao
import com.fliker.bookshelf.data.local.BooksDatabase
import com.fliker.bookshelf.data.remote.BooksApi
import com.fliker.bookshelf.data.repository.BookRepositoryImpl
import com.fliker.bookshelf.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module // Говорим Hilt: "Это модуль настройки"
@InstallIn(SingletonComponent::class) // "Эти настройки живут, пока живет приложение"
object AppModule {

    @Provides // "Я предоставляю объект..."
    @Singleton // "...в единственном экземпляре на все приложение"
    fun provideBooksApi(): BooksApi {
        // Тут мы собираем и настраиваем Retrofit
        return Retrofit.Builder()
            .baseUrl(BooksApi.BASE_URL) // Адрес из предыдущего шага
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер JSON -> Kotlin
            .build()
            .create(BooksApi::class.java) // Создаем сам API
    }

    @Provides
    @Singleton
    fun provideBooksDatabase(app: Application): BooksDatabase {
        return Room.databaseBuilder(
            app,
            BooksDatabase::class.java,
            "books_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(db: BooksDatabase): BookDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideBookRepository(api: BooksApi, dao: BookDao): BookRepository {
        return BookRepositoryImpl(api, dao)
    }
}