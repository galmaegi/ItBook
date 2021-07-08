package com.example.sendbirdapp.network

import com.example.sendbirdapp.network.model.NewResponse
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
}