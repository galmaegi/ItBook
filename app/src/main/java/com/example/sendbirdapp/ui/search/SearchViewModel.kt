package com.example.sendbirdapp.ui.search

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.network.ItBookRepository
import com.example.sendbirdapp.network.model.SearchResponse
import com.example.sendbirdapp.ui.search.model.LoadingItem
import com.example.sendbirdapp.ui.search.model.SearchItem
import com.example.sendbirdapp.ui.search.model.SearchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HistoryControl {
    fun onSelectHistory(query: String)
    fun removeHistory(query: String)
}

class SearchViewModel : ViewModel(), HistoryControl {
    private val _searchBookList = MutableLiveData<List<SearchItem>>()
    private val _historyList = MutableLiveData<Set<String>>()
    private val _searchText = MutableLiveData<String>()
    private var currentJob: Job? = null
    val searchBookList: LiveData<List<SearchItem>> = _searchBookList
    val historyList: LiveData<Set<String>> = _historyList
    val searchText: LiveData<String> = _searchText
    var searchModel: SearchModel? = null

    fun requestSearch(query: String, page: Int = 0, isSearch: Boolean = true) {
        if (isSearch) {
            _searchBookList.value = emptyList()
        }
        currentJob?.cancel()
        currentJob = CoroutineScope(Dispatchers.IO).launch {
            searchBooks(query, page).collect { response: SearchResponse ->
                Log.d("SearchViewModel", "${response}")
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

                    if (isSearch) {
                        setSearchBooksItemList(response.books)
                    } else {
                        addSearchBooksItemList(response.books)
                    }
                }
            }
        }
    }

    @MainThread
    private fun setSearchBooksItemList(newItemList: List<SearchItem>) {
        _searchBookList.value = newItemList + LoadingItem
    }

    @MainThread
    private fun addSearchBooksItemList(newItemList: List<SearchItem>) {
        _searchBookList.value = _searchBookList.value?.toMutableList()?.apply {
            addAll(size - 1, newItemList)
        } ?: newItemList + LoadingItem
    }

    @MainThread
    private fun notifyLoadEnded() {
        _searchBookList.value = _searchBookList.value?.toMutableList()?.apply {
            if (size > 0 && get(lastIndex) is LoadingItem) {
                removeLast()
            }
        }
    }

    private suspend fun searchBooks(query: String, page: Int = 0) =
        ItBookRepository.searchBooks(query, page)

    fun addHistory(query: String) {
        _historyList.value = _historyList.value?.toMutableSet()?.apply {
            if (contains(query)) {
                remove(query)
            }
            add(query)
        } ?: setOf(query)
    }

    override fun onSelectHistory(query: String) {
        _searchText.value = query
    }

    override fun removeHistory(query: String) {
        _historyList.value = _historyList.value?.toMutableSet()?.apply {
            if (contains(query)) {
                remove(query)
            }
        }
    }
}