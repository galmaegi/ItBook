package com.example.sendbirdapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var detailViewModel: DetailViewModel
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val isbn13Extra = intent.getStringExtra(EXTRA_ISBN13)
        if (isbn13Extra.isNullOrEmpty()) {
            // TODO: 2021/07/09 handle error
            finish()
            return
        }

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        Log.d("DetailActivity", "$isbn13Extra")


        CoroutineScope(Dispatchers.IO).launch {
            detailViewModel.getBooks(isbn13Extra).collect {
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