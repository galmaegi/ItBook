package com.example.sendbirdapp.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.sendbirdapp.repository.ItBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    itBookRepository: ItBookRepository
) : ViewModel() {

    val bookmarkList = itBookRepository.getAllBookmarks().asLiveData()
}