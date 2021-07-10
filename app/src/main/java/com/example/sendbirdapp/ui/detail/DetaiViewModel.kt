package com.example.sendbirdapp.ui.detail

import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.repository.ItBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ViewModel() {
    suspend fun getBooks(isbn13: String) = itBookRepository.getBooks(isbn13)
}