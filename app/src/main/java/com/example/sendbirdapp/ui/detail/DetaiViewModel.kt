package com.example.sendbirdapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.sendbirdapp.repository.ItBookRepository
import com.example.sendbirdapp.repository.db.model.Bookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val isbn13: String = savedStateHandle.get<String>(EXTRA_ISBN13)!!
    val isBookmarked: LiveData<Boolean> = itBookRepository.isBookmarked(isbn13).asLiveData()

    suspend fun getBooks(isbn13: String) = itBookRepository.getBooks(isbn13)

    fun addBookmark(isbn13: String) {
        val bookmark = Bookmark(isbn13, System.currentTimeMillis())
        CoroutineScope(Dispatchers.IO).launch {
            itBookRepository.insertBookmark(bookmark)
        }
    }

    fun removeBookmark(isbn13: String) {
        if (isBookmarked.value == true) {
            CoroutineScope(Dispatchers.IO).launch {
                itBookRepository.deleteBookmark(isbn13)
            }
        }
    }

    companion object {
        private const val EXTRA_ISBN13 = "extra_isbn13"
    }
}