package com.example.itbook.ui.newitem.model

data class NewItem(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    // ImageUrl
    val image: String,
    val url: String,
)