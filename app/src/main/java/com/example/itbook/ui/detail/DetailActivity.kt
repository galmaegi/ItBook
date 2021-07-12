package com.example.itbook.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.itbook.R
import com.example.itbook.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

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

        detailViewModel.bookDetail.observe(this) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOptionsMenu() {
        if (detailViewModel.isBookmarked.value == true) {
            detailViewModel.removeBookmark()
        } else {
            detailViewModel.addBookmark()
        }
    }

    companion object {
        internal const val EXTRA_TITLE = "extra_title"
        internal const val EXTRA_SUBTITLE = "extra_subtitle"
        internal const val EXTRA_ISBN13 = "extra_isbn13"
        internal const val EXTRA_PRICE = "extra_price"
        internal const val EXTRA_IMAGE = "extra_image"
        internal const val EXTRA_URL = "extra_url"

        fun Context.getIntent(
            title: String,
            subtitle: String,
            isbn13: String,
            price: String,
            image: String,
            url: String
        ) = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_SUBTITLE, subtitle)
            putExtra(EXTRA_ISBN13, isbn13)
            putExtra(EXTRA_PRICE, price)
            putExtra(EXTRA_IMAGE, image)
            putExtra(EXTRA_URL, url)
        }
    }
}