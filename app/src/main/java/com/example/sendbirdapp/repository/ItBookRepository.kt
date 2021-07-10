package com.example.sendbirdapp.repository

import com.example.sendbirdapp.repository.db.SearchHistoryDao
import com.example.sendbirdapp.repository.db.model.SearchHistory
import com.example.sendbirdapp.repository.network.ItBookApi
import com.example.sendbirdapp.repository.network.model.BooksResponse
import com.example.sendbirdapp.repository.network.model.NewResponse
import com.example.sendbirdapp.repository.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItBookRepository @Inject constructor(private val searchHistoryDao: SearchHistoryDao) {
    private val itBookApi: ItBookApi = ItBookApi.create()

    suspend fun getNew(): Flow<NewResponse> = flow {
        val call = itBookApi.getNew()
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    suspend fun searchBooks(query: String, page: Int = 0): Flow<SearchResponse> = flow {
        val call = itBookApi.getSearch(query, page)
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    suspend fun getBooks(isbn13: String): Flow<BooksResponse> = flow {
        val call = itBookApi.getBooks(isbn13)
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    fun getAllSearchHistories(): Flow<List<SearchHistory>> = searchHistoryDao.getAllSearchHistories()

    suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        searchHistoryDao.insertSearchHistory(searchHistory)
    }

    suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        searchHistoryDao.deleteSearchHistory(searchHistory)
    }


}