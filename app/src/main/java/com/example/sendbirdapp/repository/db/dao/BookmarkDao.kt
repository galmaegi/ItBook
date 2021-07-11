package com.example.sendbirdapp.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sendbirdapp.repository.db.model.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM Bookmark")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmark WHERE isbn13 = :isbn13 LIMIT 1)")
    fun isBookmarked(isbn13: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(searchHistoryList: Bookmark): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBookmark(searchHistoryList: Bookmark): Int

    @Query("DELETE FROM bookmark WHERE isbn13 = :isbn13")
    suspend fun deleteBookmark(isbn13: String)
}