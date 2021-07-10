package com.example.sendbirdapp.ui.search

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.sendbirdapp.repository.ItBookRepository
import com.example.sendbirdapp.repository.db.model.SearchHistory
import com.example.sendbirdapp.repository.network.model.SearchResponse
import com.example.sendbirdapp.ui.search.model.LoadingItem
import com.example.sendbirdapp.ui.search.model.SearchItem
import com.example.sendbirdapp.ui.search.model.SearchModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        itBookRepository.searchBooks(query, page)

    fun addHistory(query: String) {
        val history = SearchHistory(query, System.currentTimeMillis())
        CoroutineScope(Dispatchers.IO).launch {
            itBookRepository.insertSearchHistory(history)
        }
    }

    fun onSelectHistory(history: SearchHistory) {
        _searchText.value = history.query
    }

    fun removeHistory(history: SearchHistory) {
        _historyList.value?.firstOrNull {
            it.query == history.query
        }?.let {
            CoroutineScope(Dispatchers.IO).launch {
                itBookRepository.deleteSearchHistory(it)
            }
        }
    }
}