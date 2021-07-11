package com.example.sendbirdapp.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sendbirdapp.repository.db.dao.BookmarkDao
import com.example.sendbirdapp.repository.db.dao.SearchHistoryDao
import com.example.sendbirdapp.repository.db.model.BookmarkItem
import com.example.sendbirdapp.repository.db.model.SearchHistory

@Database(entities = [SearchHistory::class, BookmarkItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSearchHistoryDao(): SearchHistoryDao
    abstract fun getBookmarkDao(): BookmarkDao

    companion object {
        private const val DATABASE_NAME = "sendbird_db"

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