package com.example.itbook.ui.search.model

import com.example.itbook.ui.common.booklist.model.BookListItem

/*
* Marker interface
* */
interface SearchItem

data class SearchBookItem(
    override val title: String,
    override val subtitle: String,
    override val isbn13: String,
    override val price: String,
    // ImageUrl
    override val image: String,
    override val url: String
) : SearchItem, BookListItem

object SearchLoadingItem : SearchItem
