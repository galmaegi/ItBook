package com.example.itbook.ui.newitem.model

import com.example.itbook.ui.common.booklist.model.BookListItem

data class NewItem(
    override val title: String,
    override val subtitle: String,
    override val isbn13: String,
    override val price: String,
    // ImageUrl
    override val image: String,
    override val url: String
) : BookListItem