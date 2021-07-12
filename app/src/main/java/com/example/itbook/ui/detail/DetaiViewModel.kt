package com.example.itbook.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.itbook.repository.ItBookRepository
import com.example.itbook.repository.db.model.BookmarkItem
import com.example.itbook.repository.network.model.BooksResponse
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_IMAGE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_ISBN13
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_PRICE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_SUBTITLE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_TITLE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _bookDetail: MutableLiveData<BooksResponse> = MutableLiveData()
    val bookmark: BookmarkItem = BookmarkItem(
        savedStateHandle.get<String>(EXTRA_TITLE) ?: "",
        savedStateHandle.get<String>(EXTRA_SUBTITLE) ?: "",
        savedStateHandle.get<String>(EXTRA_ISBN13) ?: "",
        savedStateHandle.get<String>(EXTRA_PRICE) ?: "",
        savedStateHandle.get<String>(EXTRA_IMAGE) ?: "",
        savedStateHandle.get<String>(EXTRA_URL) ?: "",
        System.currentTimeMillis()
    )
    val isBookmarked = itBookRepository.isBookmarked(bookmark.isbn13).asLiveData()
    val bookDetail: LiveData<BooksResponse> = _bookDetail

    init {
        CoroutineScope(Dispatchers.IO).launch {
            itBookRepository.getBooks(bookmark.isbn13).collect {
                _bookDetail.postValue(it)
            }
        }
    }

    fun addBookmark() {
        CoroutineScope(Dispatchers.IO).launch {
            bookmark.access()
            itBookRepository.insertBookmark(bookmark)
        }
    }

    fun removeBookmark() {
        if (isBookmarked.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                itBookRepository.deleteBookmark(bookmark.isbn13)
            }
        }
    }

}