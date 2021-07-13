package com.example.itbook.repository.network

import com.example.itbook.repository.network.model.BooksResponse
import com.example.itbook.repository.network.model.NewResponse
import com.example.itbook.repository.network.model.SearchResponse
import retrofit2.Call

class MockItBookApi : ItBookApi {


    override fun getNew(): Call<NewResponse?>? {
        TODO("Not yet implemented")
    }

    override fun getSearch(query: String?, page: Int): Call<SearchResponse?>? {
        TODO("Not yet implemented")
    }

    override fun getBooks(isbn13: String?): Call<BooksResponse?>? {
        TODO("Not yet implemented")
    }
}