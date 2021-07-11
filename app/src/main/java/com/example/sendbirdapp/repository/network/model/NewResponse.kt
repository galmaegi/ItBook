package com.example.sendbirdapp.repository.network.model

data class NewResponse(
    val error: String,
    val total: String,
    val books: List<BookItem>
)