package com.example.itbook.ui.search.model

import com.example.itbook.repository.db.model.SearchHistory

data class HistoryItem(
    val keyword: String,
    val lastAccessedTime: Long
)

fun SearchHistory.toHistoryItem() =
    HistoryItem(
        keyword, lastAccessedTime
    )