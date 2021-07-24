package com.example.itbook.ui.search

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.itbook.ui.search.model.SearchBookItem
import com.example.itbook.ui.search.model.SearchItem
import com.example.itbook.ui.search.model.SearchLoadingItem
import com.example.itbook.ui.search.model.SearchModel
import com.example.itbookapi.ItBookRepository
import com.example.itbookapi.db.model.SearchHistory
import com.example.itbookapi.network.model.BookItem
import com.example.itbookapi.network.model.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject internal constructor(
    private val itBookRepository: ItBookRepository
) : ViewModel() {
    private val _searchBookList = MutableLiveData<List<SearchItem>>()
    private val _historyList = itBookRepository.getAllSearchHistories().asLiveData()
    private val _searchText = MutableLiveData<String>()
    private var currentJob: Job? = null
    val searchBookList: LiveData<List<SearchItem>> = _searchBookList
    val historyList: LiveData<List<SearchHistory>> = _historyList
    val searchText: LiveData<String> = _searchText
    var searchModel: SearchModel? = null

    fun requestSearch(query: String, page: Int = 0, isSearch: Boolean = true) {
        if (isSearch) {
            _searchBookList.value = emptyList()
        }
        currentJob?.cancel()
        currentJob =
            viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { context, throwable ->
                Log.d("SearchViewModel", throwable.message.toString())
            }) {
                searchBooks(query, page).collect { response: SearchResponse ->
                    Log.d("SearchViewModel", "$response")
                    searchModel = SearchModel(
                        query, response.total.toInt()
                    ).apply {
                        currentPage = response.page.toInt()
                    }
                    withContext(Dispatchers.Main) {
                        if (response.total == "0") {
                            searchModel?.isEnd = true
                            notifyLoadEnded()
                            return@withContext
                        }
                        val searchBookItemList = response.books.map {
                            it.toSearchBookItem()
                        }
                        if (isSearch) {
                            setSearchBooksItemList(searchBookItemList)
                        } else {
                            addSearchBooksItemList(searchBookItemList)
                        }
                    }
                }
            }
    }

    @MainThread
    private fun setSearchBooksItemList(newItemList: List<SearchItem>) {
        _searchBookList.value = newItemList + SearchLoadingItem
    }

    @MainThread
    private fun addSearchBooksItemList(newItemList: List<SearchItem>) {
        _searchBookList.value = _searchBookList.value?.toMutableList()?.apply {
            addAll(size - 1, newItemList)
        } ?: newItemList + SearchLoadingItem
    }

    @MainThread
    private fun notifyLoadEnded() {
        _searchBookList.value = _searchBookList.value?.toMutableList()?.apply {
            if (size > 0 && get(lastIndex) is SearchLoadingItem) {
                removeLast()
            }
        }
    }

    private suspend fun searchBooks(query: String, page: Int = 0): Flow<SearchResponse> =
        itBookRepository.searchBooks(query, page)

    fun addHistory(query: String) {
        val history = SearchHistory(query, System.currentTimeMillis())
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { context, throwable ->
            Log.d("SearchViewModel", throwable.message.toString())
        }) {
            itBookRepository.insertSearchHistory(history)
        }
    }

    fun onSelectHistory(keyword: String) {
        _searchText.value = keyword
    }

    fun removeHistory(keyword: String) {
        _historyList.value?.firstOrNull {
            it.keyword == keyword
        }?.let {
            viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { context, throwable ->
                Log.d("SearchViewModel", throwable.message.toString())
            }) {
                itBookRepository.deleteSearchHistory(keyword)
            }
        }
    }

    private fun BookItem.toSearchBookItem() = SearchBookItem(
        title,
        subtitle,
        isbn13,
        price,
        image,
        url
    )
}