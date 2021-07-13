package com.example.itbook.repository.network.model

import com.example.itbook.repository.db.model.BookDetail
import com.example.itbook.ui.detail.model.toJoinedString
import com.google.gson.JsonObject

data class BooksResponse(
    val error: String = "0",
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
    val pdf: JsonObject?
)

fun BooksResponse.toBookDetail() = BookDetail(
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
    pdf.toJoinedString()
)