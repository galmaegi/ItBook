package com.example.sendbirdapp.ui.detail

import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.network.ItBookRepository

class DetailViewModel : ViewModel() {
    suspend fun getBooks(isbn13: String) = ItBookRepository.getBooks(isbn13)
}