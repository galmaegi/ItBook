package com.example.itbook.repository.network

import android.content.Context
import com.example.itbook.repository.network.model.BooksResponse
import com.example.itbook.repository.network.model.NewResponse
import com.example.itbook.repository.network.model.SearchResponse
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.cache.CacheInterceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.File
import java.io.IOException
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
        // 60seconds
        private const val CONNECTION_TIME_OUT = 60L
        // 10Mb
        private const val CACHE_SIZE = 10L * 1024 * 1024
        private const val BASE_URL = "https://api.itbook.store/1.0/"
        private const val CACHE_DIR = "http_cache"

        fun create(context: Context, baseUrl: String = BASE_URL): ItBookApi {
            val client = OkHttpClient.Builder()
                .cache(Cache(File(context.cacheDir, CACHE_DIR), CACHE_SIZE))
                .addNetworkInterceptor(CacheInterceptor())
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                // TODO: 2021/07/10 timeout handling
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ItBookApi::class.java)
        }
    }

    class CacheInterceptor : Interceptor {
        companion object {
            // 10Minutes
            private const val CACHE_MAX_AGE = 10
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val cacheControl: CacheControl = CacheControl.Builder()
                .maxAge(CACHE_MAX_AGE, TimeUnit.MINUTES)
                .build()
            return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }
}