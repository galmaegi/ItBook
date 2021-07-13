package com.example.itbook.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BookmarkItem", indices = [Index(value = ["isbn13"], unique = true)])
data class BookmarkItem(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "isbn13")
    val isbn13: String,
    @ColumnInfo(name = "price")
    val price: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "last_accessed_time")
    var lastAccessedTime: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookmarkId: Long = 0

    fun access() {
        lastAccessedTime = System.currentTimeMillis()
    }
}