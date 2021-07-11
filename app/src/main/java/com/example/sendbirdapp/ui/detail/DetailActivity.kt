package com.example.sendbirdapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sendbirdapp.R
import com.example.sendbirdapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getStringExtra(EXTRA_ISBN13).isNullOrEmpty()) {
            // TODO: 2021/07/09 handle error
            finish()
            return
        }
        detailViewModel.isBookmarked.observe(this) {
            invalidateOptionsMenu()
        }

        getBookDetails(detailViewModel.isbn13)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmark_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (detailViewModel.isBookmarked.value == true) {
            menu?.findItem(R.id.check_bookmarked)?.setIcon(R.drawable.ic_bookmark_checked)
        } else {
            menu?.findItem(R.id.check_bookmarked)?.setIcon(R.drawable.ic_bookmark)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.check_bookmarked -> {
                updateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOptionsMenu() {
        val isbn13 = detailViewModel.isbn13
        if (detailViewModel.isBookmarked.value == true) {
            detailViewModel.removeBookmark(isbn13)
        } else {
            detailViewModel.addBookmark(isbn13)
        }
    }

    private fun getBookDetails(isbn13: String) {
        CoroutineScope(Dispatchers.IO).launch {
            detailViewModel.getBooks(isbn13).collect {
                withContext(Dispatchers.Main) {
                    Glide.with(baseContext)
                        .load(it.image)
                        .fitCenter()
                        .placeholder(R.drawable.loading_example)
                        .into(binding.image)
                    binding.title.text = it.title
                    binding.subtitle.text = it.subtitle
                    binding.authors.text = it.authors
                    binding.publisher.text = it.publisher
                    binding.isbn10.text = it.isbn10
                    binding.isbn13.text = it.isbn13
                    binding.pages.text = it.pages
                    binding.year.text = it.year
                    binding.rating.text = it.rating
                    binding.desc.text = it.desc
                    binding.price.text = it.price
                    binding.url.text = it.url
                    binding.pdf.text = it.pdf?.entrySet()?.joinToString()
                }
            }
        }
    }

    companion object {
        private const val EXTRA_ISBN13 = "extra_isbn13"

        fun Context.getIntent(isbn13: String) =
            Intent(this, DetailActivity::class.java).apply {
                putExtra(EXTRA_ISBN13, isbn13)
            }
    }
}