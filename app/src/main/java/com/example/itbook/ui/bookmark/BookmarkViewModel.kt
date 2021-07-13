package com.example.itbook.ui.bookmark

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.itbook.repository.ItBookRepository
import com.example.itbook.ui.bookmark.model.BookmarkItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    itBookRepository: ItBookRepository
) : ViewModel() {

    private val _bookmarkSortMode: MutableLiveData<BookmarkSortMode> =
        MutableLiveData(BookmarkSortMode.SORT_BY_TITLE)
    private val _bookmarkUIList = MutableLiveData<List<BookmarkItem>>()

    val bookmarkList = itBookRepository.getAllBookmarks().asLiveData()
    val bookmarkSortMode: LiveData<BookmarkSortMode> = _bookmarkSortMode
    val bookmarkUIList: LiveData<List<BookmarkItem>> = _bookmarkUIList

    @MainThread
    fun setBookmarkSortMode(mode: BookmarkSortMode) {
        _bookmarkSortMode.value = mode
    }

    @MainThread
    fun setBookmarkUIList(list: List<BookmarkItem>) {
        _bookmarkUIList.value = list
    }
}