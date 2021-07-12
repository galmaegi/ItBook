package com.example.itbook.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "SearchHistory", indices = [Index(value = ["query"], unique = true)])
data class SearchHistory(
    @ColumnInfo(name = "query")
    val query: String,
    @ColumnInfo(name = "last_accessed_time")
    val lastAccessedTime: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var searchHistoryId: Long = 0
}