package com.fliker.bookshelf.di

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
    fun provideBookRepository(api: BooksApi) : BookRepository{
        return BookRepositoryImpl(api)
    }
}