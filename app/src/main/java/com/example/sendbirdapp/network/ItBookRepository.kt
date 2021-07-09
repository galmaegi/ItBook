package com.example.sendbirdapp.network

import com.example.sendbirdapp.network.model.NewResponse
import com.example.sendbirdapp.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItBookRepository {
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
}