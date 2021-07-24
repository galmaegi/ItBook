package com.example.itbookapi.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.itbookapi.db.AppDatabase
import com.example.itbookapi.db.model.SearchHistory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchHistoryDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var searchHistoryDao: SearchHistoryDao

    private val searchHistoryA = SearchHistory(keyword = "Kotlin", 0)
    private val searchHistoryB = SearchHistory(keyword = "Android", 1)
    private val searchHistoryC = SearchHistory(keyword = "Python", 2)

    @Before
    fun createDb() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
            searchHistoryDao = database.getSearchHistoryDao()

            searchHistoryDao.insertSearchHistory(searchHistoryA)
            searchHistoryDao.insertSearchHistory(searchHistoryB)
        }
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetAllHistories() {
        runBlocking {
            assertThat(searchHistoryDao.getAllSearchHistories().first().size, equalTo(2))
            searchHistoryDao.insertSearchHistory(searchHistoryC)
            assertThat(searchHistoryDao.getAllSearchHistories().first().size, equalTo(3))
        }
    }

    @Test
    fun testDeleteHistory() {
        runBlocking {
            searchHistoryDao.deleteSearchHistory(searchHistoryA.keyword)
            assertFalse(searchHistoryDao.getAllSearchHistories().first()[0] == searchHistoryA)
        }
    }

}