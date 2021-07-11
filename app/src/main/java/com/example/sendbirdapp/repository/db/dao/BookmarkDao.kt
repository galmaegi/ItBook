package com.example.sendbirdapp.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sendbirdapp.repository.db.model.BookmarkItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM BookmarkItem")
    fun getAllBookmarks(): Flow<List<BookmarkItem>>

    @Query("SELECT EXISTS(SELECT 1 FROM BookmarkItem WHERE isbn13 = :isbn13 LIMIT 1)")
    fun isBookmarked(isbn13: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(searchHistoryList: BookmarkItem): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBookmark(searchHistoryList: BookmarkItem): Int

    @Query("DELETE FROM BookmarkItem WHERE isbn13 = :isbn13")
    suspend fun deleteBookmark(isbn13: String)
}