package com.example.itbook.repository.network

import com.example.itbook.repository.network.model.BooksResponse
import com.example.itbook.repository.network.model.NewResponse
import com.example.itbook.repository.network.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ItBookApi {
    // Get new releases books
    @GET("new")
    fun getNew(): Call<NewResponse?>?

    // Search books by title, author, ISBN or keywords
    @GET("search/{query}/{page}")
    fun getSearch(
        @Path("query") query: String?,
        @Path("page") page: Int = 0
    ): Call<SearchResponse?>?

    // Get book details by ISBN
    @GET("books/{isbn13}")
    fun getBooks(
        @Path("isbn13") isbn13: String?
    ): Call<BooksResponse?>?

    companion object {
        fun create(baseUrl: String = "https://api.itbook.store/1.0/"): ItBookApi {

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                // TODO: 2021/07/10 timeout handling
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ItBookApi::class.java)
        }
    }

}