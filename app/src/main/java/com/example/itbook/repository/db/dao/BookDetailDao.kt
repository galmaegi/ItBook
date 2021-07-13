package com.example.itbook.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.itbook.repository.db.model.BookDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDetailDao {
    @Query("SELECT * FROM BookDetail")
    fun getAllBookDetail(): Flow<List<BookDetail>>

    @Query("SELECT * FROM BookDetail WHERE isbn13 = :isbn13")
    fun getBookDetail(isbn13: String): Flow<BookDetail>

    @Query("SELECT EXISTS(SELECT 1 FROM BookDetail WHERE isbn13 = :isbn13 LIMIT 1)")
    fun isBookDetailAvailable(isbn13: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookDetail(bookDetail: BookDetail): Long

    @Query("DELETE FROM BookDetail WHERE isbn13 = :isbn13")
    suspend fun deleteBookDetail(isbn13: String)
}