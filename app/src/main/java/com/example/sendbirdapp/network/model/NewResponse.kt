package com.example.sendbirdapp.network.model

import com.example.sendbirdapp.ui.new.NewItem

data class NewResponse(
    val error: String,
    val total: String,
    val books: List<NewItem>
)