package com.example.itbook.ui.bookmark.model

import com.example.itbook.ui.common.booklist.model.BookListItem
import com.example.itbookapi.db.model.BookDetail

data class BookmarkItem(
    override val title: String,
    override val subtitle: String,
    override val isbn13: String,
    override val price: String,
    override val image: String,
    override val url: String,
    var lastAccessedTime: Long
) : BookListItem

fun BookDetail.toBookmarkItem() = BookmarkItem(
    title,
    subtitle,
    isbn13,
    price,
    image,
    url,
    lastAccessedTime
)
