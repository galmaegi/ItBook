package com.example.itbook.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.itbook.ui.bookmark.model.BookmarkItem
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_IMAGE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_ISBN13
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_PRICE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_SUBTITLE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_TITLE
import com.example.itbook.ui.detail.DetailActivity.Companion.EXTRA_URL
import com.example.itbook.ui.detail.model.BookDetailItem
import com.example.itbook.ui.detail.model.toBookDetailItem
import com.example.itbookapi.ItBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _bookDetailItem: MutableLiveData<BookDetailItem> = MutableLiveData()
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
    val bookDetailItem: LiveData<BookDetailItem> = _bookDetailItem

    init {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            Log.d("DetailViewModel", throwable.message.toString())
        }) {
            itBookRepository.getBooks(bookmark.isbn13).collect {
                _bookDetailItem.postValue(it.toBookDetailItem())
            }
        }
    }

    fun updateMemo(memo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                itBookRepository.updateMemo(bookmark.isbn13, memo)
            }
        }
    }

    fun addBookmark() {
        if (isBookmarked.value == false) {
            viewModelScope.launch(Dispatchers.IO) {
                itBookRepository.updateBookMarked(bookmark.isbn13, true)
            }
        }
    }

    fun removeBookmark() {
        if (isBookmarked.value == true) {
            viewModelScope.launch(Dispatchers.IO) {
                itBookRepository.updateBookMarked(bookmark.isbn13, false)
            }
        }
    }
}