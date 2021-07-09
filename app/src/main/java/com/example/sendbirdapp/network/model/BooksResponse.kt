package com.example.sendbirdapp.network.model

import com.google.gson.JsonObject

data class BooksResponse(
    val error: String,
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val isbn10: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: String,
    val desc: String,
    val price: String,
    // image url
    val image: String,
    val url: String,
    // seems optional
    val pdf: JsonObject?
)