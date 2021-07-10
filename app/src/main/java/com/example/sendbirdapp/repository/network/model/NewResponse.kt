package com.example.sendbirdapp.repository.network.model

import com.example.sendbirdapp.ui.newitem.NewItem

data class NewResponse(
    val error: String,
    val total: String,
    val books: List<NewItem>
)