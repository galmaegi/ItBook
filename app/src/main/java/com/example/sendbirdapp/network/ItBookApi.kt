package com.example.sendbirdapp.network

import com.example.sendbirdapp.network.model.NewResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ItBookApi {
    // Get new releases books
    @GET("new")
    fun getNew(): Call<NewResponse?>?

    // Search books by title, author, ISBN or keywords
    @GET("search/{query}/{page}")
    fun getSearch(
        @Path("query") query: String?,
        @Path("page") page: Int?
    ): Call<List<String>?>?

    // Get book details by ISBN
    @GET("books/{isbn13}")
    fun getBooks(
        @Path("isbn13") isbn13: String?
    )

    companion object {
        private const val BASE_URL = "https://api.itbook.store/1.0/"

        fun create(): ItBookApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ItBookApi::class.java)
        }
    }

}