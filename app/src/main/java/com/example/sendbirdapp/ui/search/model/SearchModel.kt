package com.example.sendbirdapp.ui.search.model

data class SearchModel(
    val query: String,
    val totalPage: Int,
) {
    var currentPage: Int = 0
    var isEnd = false
}