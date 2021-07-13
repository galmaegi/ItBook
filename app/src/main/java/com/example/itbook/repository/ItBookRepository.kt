package com.example.itbook.repository

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.itbook.repository.db.dao.BookmarkDao
import com.example.itbook.repository.db.dao.SearchHistoryDao
import com.example.itbook.repository.db.model.BookmarkItem
import com.example.itbook.repository.db.model.SearchHistory
import com.example.itbook.repository.network.ItBookApi
import com.example.itbook.repository.network.model.BooksResponse
import com.example.itbook.repository.network.model.NewResponse
import com.example.itbook.repository.network.model.SearchResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItBookRepository @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val bookmarkDao: BookmarkDao,
    @ApplicationContext context: Context
) {
    private val itBookApi: ItBookApi = ItBookApi.create(context)

    @WorkerThread
    suspend fun getNew(): Flow<NewResponse> = flow {
        val call = itBookApi.getNew()
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    @WorkerThread
    suspend fun searchBooks(query: String, page: Int = 0): Flow<SearchResponse> = flow {
        val call = itBookApi.getSearch(query, page)
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    @WorkerThread
    suspend fun getBooks(isbn13: String): Flow<BooksResponse> = flow {
        val call = itBookApi.getBooks(isbn13)
        val response = call?.execute()
        response?.body()?.let {
            emit(it)
        }
    }

    fun getAllSearchHistories(): Flow<List<SearchHistory>> =
        searchHistoryDao.getAllSearchHistories()

    suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        searchHistoryDao.insertSearchHistory(searchHistory)
    }

    suspend fun deleteSearchHistory(keyword: String) {
        searchHistoryDao.deleteSearchHistory(keyword)
    }

    fun getAllBookmarks(): Flow<List<BookmarkItem>> = bookmarkDao.getAllBookmarks()

    fun isBookmarked(isbn13: String): Flow<Boolean> = bookmarkDao.isBookmarked(isbn13)

    suspend fun insertBookmark(bookmark: BookmarkItem) {
        bookmarkDao.insertBookmark(bookmark)
    }

    suspend fun deleteBookmark(isbn13: String) {
        bookmarkDao.deleteBookmark(isbn13)
    }
}