package com.example.sendbirdapp.ui.search.model

/*
* Marker interface
* */
interface SearchItem

data class SearchBookItem(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    // ImageUrl
    val image: String,
    val url: String,
) : SearchItem

object SearchLoadingItem : SearchItem
