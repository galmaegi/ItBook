package com.example.itbook.repository.network.model

data class NewResponse(
    val error: String = "0",
    val total: String = "0",
    val books: List<BookItem> = listOf()
)