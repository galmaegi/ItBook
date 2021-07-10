package com.example.sendbirdapp.repository.network.model

import com.example.sendbirdapp.ui.search.model.BookItem

data class SearchResponse(
    val error: String,
    val total: String,
    val page: String,
    val books: List<BookItem>
)