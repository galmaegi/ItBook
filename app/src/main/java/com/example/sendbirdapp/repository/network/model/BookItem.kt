package com.example.sendbirdapp.repository.network.model

data class BookItem(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    // ImageUrl
    val image: String,
    val url: String,
)