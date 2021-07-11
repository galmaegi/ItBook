package com.example.sendbirdapp.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sendbirdapp.repository.db.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM SearchHistory")
    fun getAllSearchHistories(): Flow<List<SearchHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistoryList: SearchHistory): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSearchHistory(searchHistoryList: SearchHistory): Int

    @Delete
    suspend fun deleteSearchHistory(searchHistoryList: SearchHistory)
}