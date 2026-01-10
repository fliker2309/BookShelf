package com.fliker.bookshelf.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fliker.bookshelf.data.local.entity.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1
)
abstract class BooksDatabase : RoomDatabase(){
    abstract val dao: BookDao

}