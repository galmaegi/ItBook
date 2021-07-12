package com.example.itbook.ui.bookmark

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.itbook.repository.ItBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    itBookRepository: ItBookRepository
) : ViewModel() {

    private val _bookmarkSortMode: MutableLiveData<BookmarkSortMode> =
        MutableLiveData(BookmarkSortMode.SORT_BY_TITLE)
    val bookmarkList = itBookRepository.getAllBookmarks().asLiveData()
    val bookmarkSortMode: LiveData<BookmarkSortMode> = _bookmarkSortMode

    @MainThread
    fun setBookmarkSortMode(mode: BookmarkSortMode) {
        _bookmarkSortMode.value = mode
    }
}