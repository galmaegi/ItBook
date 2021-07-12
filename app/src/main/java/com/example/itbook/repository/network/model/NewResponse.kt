package com.example.itbook.repository.network.model

data class NewResponse(
    val error: String,
    val total: String,
    val books: List<BookItem>
)