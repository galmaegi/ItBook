package com.example.itbook.repository.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.itbook.repository.db.AppDatabase
import com.example.itbook.repository.db.model.BookmarkItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookmarkDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var bookmarkDao: BookmarkDao

    private val bookmarkA = BookmarkItem(
        "Programming Kotlin",
        "Create Elegant, Expressive, and Performant JVM and Android Applications",
        "9781680506358",
        "$38.99",
        "https://itbook.store/img/books/9781680506358.png",
        "https://itbook.store/books/9781680506358",
        0
    )
    private val bookmarkB = BookmarkItem(
        "Programming DSLs in Kotlin",
        "Design Expressive and Robust Special Purpose Code",
        "9781680507935",
        "$0.00",
        "https://itbook.store/img/books/9781680507935.png",
        "https://itbook.store/books/9781680507935",
        1
    )
    private val bookmarkC = BookmarkItem(
        "Kotlin Quick Start Guide",
        "Core features to get you ready for developing applications",
        "9781789344189",
        "$19.73",
        "https://itbook.store/img/books/9781789344189.png",
        "https://itbook.store/books/9781789344189",
        2
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
            bookmarkDao = database.getBookmarkDao()

            // Insert plants in non-alphabetical order to test that results are sorted by name
            bookmarkDao.insertBookmark(bookmarkA)
            bookmarkDao.insertBookmark(bookmarkB)
        }
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testIsBookmarked() {
        runBlocking {
            assertTrue(bookmarkDao.isBookmarked(bookmarkA.isbn13).first())
            assertTrue(bookmarkDao.isBookmarked(bookmarkB.isbn13).first())
            assertFalse(bookmarkDao.isBookmarked("978168050635821").first())
        }
    }

    @Test
    fun testGetAllBookmarks() {
        runBlocking {
            assertThat(bookmarkDao.getAllBookmarks().first().size, equalTo(2))
            assertThat(bookmarkDao.getAllBookmarks().first().sortedBy {
                it.lastAccessedTime
            }[0], equalTo(bookmarkA))
            assertThat(bookmarkDao.getAllBookmarks().first().sortedBy {
                it.lastAccessedTime
            }[1], equalTo(bookmarkB))

            bookmarkDao.insertBookmark(bookmarkC)
            assertThat(bookmarkDao.getAllBookmarks().first().size, equalTo(3))
            assertThat(bookmarkDao.getAllBookmarks().first().sortedBy {
                it.lastAccessedTime
            }[2], equalTo(bookmarkC))
        }
    }

    @Test
    fun testDeleteBookmark() {
        runBlocking {
            assertTrue(bookmarkDao.isBookmarked(bookmarkA.isbn13).first())
            bookmarkDao.deleteBookmark(bookmarkA.isbn13)
            assertFalse(bookmarkDao.isBookmarked(bookmarkA.isbn13).first())
        }
    }
}

