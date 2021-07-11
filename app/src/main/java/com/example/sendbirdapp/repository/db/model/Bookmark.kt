package com.example.sendbirdapp.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Bookmark", indices = [Index(value = ["isbn13"], unique = true)])
data class Bookmark(
    @ColumnInfo(name = "isbn13")
    val isbn13: String,
    @ColumnInfo(name = "last_accessed_time")
    val lastAccessedTime: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookmarkId: Long = 0
}