package com.example.itbookapi.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.itbookapi.db.AppDatabase
import com.example.itbookapi.db.model.BookDetail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDetailDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var bookmarkDao: BookDetailDao

    private val bookmarkA = BookDetail(
        "TypeScript Notes for Professionals",
        "",
        "Stack Overflow Community",
        "Self-publishing",
        "English",
        "1622115724",
        "1001622115721",
        "96",
        "2018",
        "0",
        "The TypeScript Notes for Professionals book is compiled from Stack Overflow Documentation, the content is written by the beautiful people at Stack Overflow....",
        "$0.00",
        "https://itbook.store/img/books/1001622115721.png",
        "https://itbook.store/books/1001622115721",
        "Free eBook : https://www.dbooks.org/d/5592544360-1622115253-9bbc1cd0a894d0c9/"
    )
    private val bookmarkB = BookDetail(
        "Python Notes for Professionals",
        "",
        "Stack Overflow Community",
        "Self-publishing",
        "English",
        "1621860582",
        "1001621860589",
        "855",
        "2018",
        "0",
        "The Python Notes for Professionals book is compiled from Stack Overflow Documentation, the content is written by the beautiful people at Stack Overflow....",
        "$0.00",
        "https://itbook.store/img/books/1001621860589.png",
        "https://itbook.store/books/1001621860589",
        "Free eBook : https://www.dbooks.org/d/5591650063-1621860247-f0dcab9437a281b1/"
    )
    private val bookmarkC = BookDetail(
        "MySQL Notes for Professionals",
        "",
        "Stack Overflow Community",
        "Self-publishing",
        "English",
        "1620983362",
        "1001620983366",
        "198",
        "2018",
        "0",
        "The MySQL Notes for Professionals book is compiled from Stack Overflow Documentation, the content is written by the beautiful people at Stack Overflow....",
        "$0.00",
        "https://itbook.store/img/books/1001620983366.png",
        "https://itbook.store/books/1001620983366",
        "Free eBook : https://www.dbooks.org/d/1530-1620980600-907cc47180aeccfc/"
    )

    @Before
    fun createDb() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
            bookmarkDao = database.getBookmarkDao()

            // Insert plants in non-alphabetical order to test that results are sorted by name
            bookmarkDao.insertBookDetail(bookmarkA)
            bookmarkDao.insertBookDetail(bookmarkB)
        }
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testIsBookDetailAvailable() {
        runBlocking {
            assertTrue(bookmarkDao.isBookDetailAvailable(bookmarkA.isbn13).first())
            assertTrue(bookmarkDao.isBookDetailAvailable(bookmarkB.isbn13).first())
            assertFalse(bookmarkDao.isBookDetailAvailable("978168050635821").first())
        }
    }

    @Test
    fun testGetAllBookDetail() {
        runBlocking {
            assertThat(bookmarkDao.getAllBookDetail().first().size, equalTo(2))
            assertThat(bookmarkDao.getAllBookDetail().first().sortedBy {
                it.lastAccessedTime
            }[0], equalTo(bookmarkA))
            assertThat(bookmarkDao.getAllBookDetail().first().sortedBy {
                it.lastAccessedTime
            }[1], equalTo(bookmarkB))

            bookmarkDao.insertBookDetail(bookmarkC)
            assertThat(bookmarkDao.getAllBookDetail().first().size, equalTo(3))
            assertThat(bookmarkDao.getAllBookDetail().first().sortedBy {
                it.lastAccessedTime
            }[2], equalTo(bookmarkC))
        }
    }

    @Test
    fun testGetAllBookMarked() {
        runBlocking {
            assertThat(bookmarkDao.getAllBookMarked().first().size, equalTo(0))
            bookmarkDao.updateBookMarked(bookmarkA.isbn13, true)
            assertThat(bookmarkDao.getAllBookMarked().first().size, equalTo(1))
        }
    }

    @Test
    fun testGetBookDetail() {
        runBlocking {
            assertThat(
                bookmarkDao.getBookDetail(bookmarkA.isbn13).first().isbn13,
                equalTo(bookmarkA.isbn13)
            )
        }
    }

    @Test
    fun testIsBookmarked() {
        runBlocking {
            assertThat(bookmarkDao.isBookmarked(bookmarkA.isbn13).first(), equalTo(false))
            bookmarkDao.updateBookMarked(bookmarkA.isbn13, true)
            assertThat(bookmarkDao.isBookmarked(bookmarkA.isbn13).first(), equalTo(true))
        }
    }

    @Test
    fun testUpdateMemo() {
        runBlocking {
            val memoTestString = "123ABC!@#"
            bookmarkDao.updateMemo(bookmarkA.isbn13, memoTestString)
            assertThat(
                bookmarkDao.getBookDetail(bookmarkA.isbn13).first().memo,
                equalTo(memoTestString)
            )
        }
    }

    @Test
    fun testDeleteBookmark() {
        runBlocking {
            assertTrue(bookmarkDao.isBookDetailAvailable(bookmarkA.isbn13).first())
            bookmarkDao.deleteBookDetail(bookmarkA.isbn13)
            assertFalse(bookmarkDao.isBookDetailAvailable(bookmarkA.isbn13).first())
        }
    }
}

