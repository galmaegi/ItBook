package com.example.itbook.ui.bookmark

import com.example.itbook.repository.db.model.BookmarkItem

enum class BookmarkSortMode(val desc: String, val comparator: Comparator<BookmarkItem>) {
    SORT_BY_TITLE("Title", Comparator<BookmarkItem> { o1, o2 ->
        o1.title.compareTo(o2.title)
    }),
    SORT_BY_ISBN("ISBN", Comparator<BookmarkItem> { o1, o2 ->
        o1.isbn13.compareTo(o2.isbn13)
    }),
    SORT_BY_PRICE("Price", Comparator<BookmarkItem> { o1, o2 ->
        o1.price.compareTo(o2.price)
    }),
    SORT_BY_TIME("Bookmarked time", Comparator<BookmarkItem> { o1, o2 ->
        o1.lastAccessedTime.compareTo(o2.lastAccessedTime)
    })
}
