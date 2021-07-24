package com.example.itbook.common.extensions

import com.example.itbook.ui.newitem.model.NewItem
import com.example.itbook.ui.search.model.SearchBookItem
import com.example.itbookapi.network.model.BookItem

fun BookItem.toSearchBookItem() = SearchBookItem(
    title,
    subtitle,
    isbn13,
    price,
    image,
    url
)

fun BookItem.toNewItem() = NewItem(title, subtitle, isbn13, price, image, url)
