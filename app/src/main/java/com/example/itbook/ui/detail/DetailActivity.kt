package com.example.itbook.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.itbook.R
import com.example.itbook.common.extensions.bindImage
import com.example.itbook.common.extensions.setCompressedRawBitmap
import com.example.itbook.databinding.ActivityDetailBinding
import com.example.itbook.ui.detail.memo.MemoDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val guideToast by lazy {
        Toast.makeText(this, "Click image to leave memo", Toast.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getStringExtra(EXTRA_ISBN13).isNullOrEmpty()) {
            // TODO: 2021/07/09 handle error
            finish()
            return
        }

        val bitmap = intent.getByteArrayExtra(EXTRA_IMAGE_BITMAP)
        if (bitmap != null) {
            binding.image.setCompressedRawBitmap(bitmap)
        } else {
            binding.image.bindImage(detailViewModel.bookmark.image)
        }

        binding.image.setOnClickListener {
            MemoDialog(this, detailViewModel.bookDetailItem.value?.memo ?: "").apply {
                onSaveClickListener = {
                    detailViewModel.updateMemo(it)
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                }
            }.show()
        }

        detailViewModel.isBookmarked.observe(this) {
            invalidateOptionsMenu()
        }

        detailViewModel.bookDetailItem.observe(this) {
            binding.detailItem = it
        }

        guideToast.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        guideToast.cancel()
        if (detailViewModel.isBookmarked.value == false &&
            intent.getBooleanExtra(
                EXTRA_IS_REMOVABLE_FROM_LIST, false
            )
        ) {
            finish()
            overridePendingTransition(0, android.R.anim.fade_out)
        } else {
            finishAfterTransition()
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
        internal const val EXTRA_IMAGE_BITMAP = "extra_image_bitmap"
        internal const val EXTRA_IS_REMOVABLE_FROM_LIST = "extra_is_removable_from_list"

        fun Context.getDetailActivityIntent(
            title: String,
            subtitle: String,
            isbn13: String,
            price: String,
            image: String,
            url: String,
            imageBitmap: ByteArray? = null,
            isRemovableFromList: Boolean = false
        ) = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_SUBTITLE, subtitle)
            putExtra(EXTRA_ISBN13, isbn13)
            putExtra(EXTRA_PRICE, price)
            putExtra(EXTRA_IMAGE, image)
            putExtra(EXTRA_URL, url)
            putExtra(EXTRA_IMAGE_BITMAP, imageBitmap)
            putExtra(EXTRA_IS_REMOVABLE_FROM_LIST, isRemovableFromList)
        }
    }
}