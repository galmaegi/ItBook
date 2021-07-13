package com.example.itbook.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "BookDetail", indices = [Index(value = ["isbn13"], unique = true)])
data class BookDetail(
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "subtitle")
    val subtitle: String = "",
    @ColumnInfo(name = "authors")
    val authors: String = "",
    @ColumnInfo(name = "publisher")
    val publisher: String = "",
    @ColumnInfo(name = "language")
    val language: String = "",
    @ColumnInfo(name = "isbn10")
    val isbn10: String = "",
    @ColumnInfo(name = "isbn13")
    val isbn13: String = "",
    @ColumnInfo(name = "pages")
    val pages: String = "",
    @ColumnInfo(name = "year")
    val year: String = "",
    @ColumnInfo(name = "rating")
    val rating: String = "",
    @ColumnInfo(name = "desc")
    val desc: String = "",
    @ColumnInfo(name = "price")
    val price: String = "",
    // image url
    @ColumnInfo(name = "image")
    val image: String = "",
    @ColumnInfo(name = "url")
    val url: String = "",
    // seems optional
    @ColumnInfo(name = "pdf")
    val pdf: String = "",
    @ColumnInfo(name = "lastAccessedTime")
    var lastAccessedTime: Long = System.currentTimeMillis()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var bookmarkId: Long = 0
}