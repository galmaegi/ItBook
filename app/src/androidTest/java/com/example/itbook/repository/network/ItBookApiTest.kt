package com.example.itbook.repository.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItBookApiTest {
    private lateinit var itBookApi: ItBookApi

    @Before
    fun createApi() {
        itBookApi = ItBookApi.create()
    }

    @Test
    fun testGetNew() {
        runBlocking {
            val call = itBookApi.getNew()
            assertNotNull(call)
            val response = call!!.execute()
            assertNotNull(response)
            val body = response.body()
            assertNotNull(body)
            assertThat(body!!.error, equalTo("0"))
        }
    }

    @Test
    fun testGetSearch() {
        runBlocking {
            val call = itBookApi.getSearch("Android")
            assertNotNull(call)
            val response = call!!.execute()
            assertNotNull(response)
            val body = response.body()
            assertNotNull(body)
            assertThat(body!!.error, equalTo("0"))
        }
    }

    @Test
    fun testGetSearchWithPage() {
        runBlocking {
            val call = itBookApi.getSearch("Android", Int.MAX_VALUE)
            assertNotNull(call)
            val response = call!!.execute()
            assertNotNull(response)
            val body = response.body()
            assertNotNull(body)
            assertThat(body!!.error, equalTo("0"))
            assertThat(body.total, equalTo("0"))
            assertThat(body.books.size, equalTo(0))
        }
    }

    @Test
    fun testGetBooks() {
        runBlocking {
            val call = itBookApi.getBooks("9781680506358")
            assertNotNull(call)
            val response = call!!.execute()
            assertNotNull(response)
            val body = response.body()
            assertNotNull(body)
            assertThat(body!!.error,  equalTo("0"))
            assertThat(body.title, equalTo("Programming Kotlin"))
            assertThat(body.subtitle, equalTo("Create Elegant, Expressive, and Performant JVM and Android Applications"))
            assertThat(body.authors, equalTo("Venkat Subramaniam"))
            assertThat(body.publisher, equalTo("The Pragmatic Programmers"))
            assertThat(body.language, equalTo("English"))
            assertThat(body.isbn10, equalTo("1680506358"))
            assertThat(body.isbn13, equalTo("9781680506358"))
            assertThat(body.pages, equalTo("460"))
            assertThat(body.year, equalTo("2019"))
            assertThat(body.rating, equalTo("5"))
            assertThat(body.desc, equalTo("Programmers don't just use Kotlin, they love it. Even Google has adopted it as a first-class language for Android development. With Kotlin, you can intermix imperative, functional, and object-oriented styles of programming and benefit from the approach that's most suitable for the problem at hand. L..."))
            assertThat(body.price, equalTo("$38.99"))
            assertThat(body.image, equalTo("https://itbook.store/img/books/9781680506358.png"))
            assertThat(body.url, equalTo("https://itbook.store/books/9781680506358"))
        }
    }
}