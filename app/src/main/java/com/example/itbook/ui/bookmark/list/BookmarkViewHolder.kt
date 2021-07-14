package com.example.itbook.ui.bookmark.list

import android.content.Context
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.common.extensions.getActivityContext
import com.example.itbook.common.extensions.getCompressedRawBitmap
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.ui.bookmark.model.BookmarkItem
import com.example.itbook.ui.detail.DetailActivity.Companion.getDetailActivityIntent

class BookmarkViewHolder(
    private val binding: ItemBookBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    fun onBind(item: BookmarkItem) {
        binding.bookItem = item
        binding.root.setOnClickListener {
            val options = context.getActivityContext()?.let {
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    it,
                    binding.image,
                    binding.image.transitionName
                )
            }
            context.startActivity(
                context.getDetailActivityIntent(
                    item.title,
                    item.subtitle,
                    item.isbn13,
                    item.price,
                    item.image,
                    item.url,
                    binding.image.getCompressedRawBitmap(),
                    true
                ),
                options?.toBundle()
            )
        }
    }
}