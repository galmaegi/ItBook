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

class SearchViewModel : ViewModel() {

    private val _searchBookList = MutableLiveData<List<SearchItem>>()
    private var currentJob: Job? = null
    val searchBookList: LiveData<List<SearchItem>> = _searchBookList
    var searchModel: SearchModel? = null

    fun requestSearch(query: String, page: Int = 0, isSearch: Boolean = true) {
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
            removeLast()
            addAll(newItemList + LoadingItem)
        } ?: newItemList + LoadingItem
    }

    @MainThread
    private fun notifyLoadEnded() {
        _searchBookList.value = _searchBookList.value?.toMutableList()?.apply {
            removeLast()
        }
    }

    private suspend fun searchBooks(query: String, page: Int = 0) =
        ItBookRepository.searchBooks(query, page)
}