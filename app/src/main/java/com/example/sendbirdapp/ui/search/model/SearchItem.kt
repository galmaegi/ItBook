package com.example.sendbirdapp.ui.search.model


open class SearchItem

data class BookItem(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    // ImageUrl
    val image: String,
    val url: String,
) : SearchItem()

object LoadingItem : SearchItem()
