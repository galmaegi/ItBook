package com.example.itbook.ui.common.booklist.model

interface BookListItem {
    val title: String
    val subtitle: String
    val isbn13: String
    val price: String

    // ImageUrl
    val image: String
    val url: String
}