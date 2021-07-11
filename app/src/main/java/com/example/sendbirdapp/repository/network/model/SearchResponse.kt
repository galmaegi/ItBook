package com.example.sendbirdapp.repository.network.model

data class SearchResponse(
    val error: String,
    val total: String,
    val page: String,
    val books: List<BookItem>
)