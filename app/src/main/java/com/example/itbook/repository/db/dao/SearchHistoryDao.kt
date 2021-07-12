package com.example.itbook.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.itbook.repository.db.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory")
    fun getAllSearchHistories(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistoryList: SearchHistory): Long

    @Query("DELETE FROM SearchHistory WHERE keyword = :keyword")
    suspend fun deleteSearchHistory(keyword: String)
}