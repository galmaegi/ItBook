package com.example.itbook.ui.detail.model

import com.example.itbookapi.db.model.BookDetail

data class BookDetailItem(
    val title: String = "",
    val subtitle: String = "",
    val authors: String = "",
    val publisher: String = "",
    val language: String = "",
    val isbn10: String = "",
    val isbn13: String = "",
    val pages: String = "",
    val year: String = "",
    val rating: String = "",
    val desc: String = "",
    val price: String = "",
    // image url
    val image: String = "",
    val url: String = "",
    // seems optional
    val pdf: String = "",
    var memo: String = "",
    // only available when book detail item is in db
    var lastAccessedTime: Long = 0,
    val isBookMarked: Boolean = false
)

fun BookDetail.toBookDetailItem() =
    BookDetailItem(
        title,
        subtitle,
        authors,
        publisher,
        language,
        isbn10,
        isbn13,
        pages,
        year,
        rating,
        desc,
        price,
        image,
        url,
        pdf,
        memo,
        isBookMarked = isBookMarked
    )