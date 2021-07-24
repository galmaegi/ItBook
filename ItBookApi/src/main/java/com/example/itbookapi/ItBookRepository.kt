package com.example.itbookapi

import android.content.Context
import androidx.annotation.WorkerThread
import com.example.itbookapi.db.dao.BookDetailDao
import com.example.itbookapi.db.dao.SearchHistoryDao
import com.example.itbookapi.db.model.BookDetail
import com.example.itbookapi.db.model.SearchHistory
import com.example.itbookapi.network.ItBookApi
import com.example.itbookapi.network.model.NewResponse
import com.example.itbookapi.network.model.SearchResponse
import com.example.itbookapi.network.model.toBookDetail
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItBookRepository @Inject internal constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val bookmarkDao: BookDetailDao,
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
    suspend fun getBooks(isbn13: String): Flow<BookDetail> = flow {
        val bookDetailFlow = getBookDetailCached(isbn13)
        if (bookDetailFlow.firstOrNull() != null) {
            emitAll(bookDetailFlow)
        } else {
            val call = itBookApi.getBooks(isbn13)
            val response = call?.execute()
            response?.body()?.let {
                insertBookDetail(it.toBookDetail())
                emit(it.toBookDetail())
            }
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

    fun getAllBookDetail(): Flow<List<BookDetail>> = bookmarkDao.getAllBookDetail()

    fun getAllBookMarked(): Flow<List<BookDetail>> = bookmarkDao.getAllBookMarked()

    private fun getBookDetailCached(isbn13: String): Flow<BookDetail> =
        bookmarkDao.getBookDetail(isbn13)

    fun isBookmarked(isbn13: String): Flow<Boolean> =
        bookmarkDao.isBookmarked(isbn13)

    fun isBookDetailAvailable(isbn13: String): Flow<Boolean> =
        bookmarkDao.isBookDetailAvailable(isbn13)

    suspend fun insertBookDetail(bookDetail: BookDetail) {
        bookmarkDao.insertBookDetail(bookDetail)
    }

    suspend fun deleteBookDetail(isbn13: String) {
        bookmarkDao.deleteBookDetail(isbn13)
    }

    suspend fun updateBookMarked(isbn13: String, isBookMarked: Boolean = false) {
        bookmarkDao.updateBookMarked(isbn13, isBookMarked)
    }

    suspend fun updateMemo(isbn13: String, memo: String) {
        bookmarkDao.updateMemo(isbn13, memo)
    }
}