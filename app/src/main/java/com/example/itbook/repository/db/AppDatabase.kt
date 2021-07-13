package com.example.itbook.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.itbook.repository.db.dao.BookDetailDao
import com.example.itbook.repository.db.dao.SearchHistoryDao
import com.example.itbook.repository.db.model.BookDetail
import com.example.itbook.repository.db.model.SearchHistory

@Database(entities = [SearchHistory::class, BookDetail::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSearchHistoryDao(): SearchHistoryDao
    abstract fun getBookmarkDao(): BookDetailDao

    companion object {
        private const val DATABASE_NAME = "itbook_db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}