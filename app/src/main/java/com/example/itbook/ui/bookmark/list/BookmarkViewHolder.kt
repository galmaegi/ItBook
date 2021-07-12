package com.example.itbook.ui.bookmark.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.itbook.R
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.repository.db.model.BookmarkItem
import com.example.itbook.ui.detail.DetailActivity.Companion.getIntent

class BookmarkViewHolder(
    private val binding: ItemBookBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    fun onBind(item: BookmarkItem) {
        Glide.with(context)
            .load(item.image)
            .fitCenter()
            .placeholder(R.drawable.loading_example)
            .into(binding.image)

        binding.groupTitle.visibility = item.title.takeUnless { it.isNullOrEmpty() }?.let {
            binding.title.text = it
            View.VISIBLE
        } ?: View.GONE
        binding.groupSubtitle.visibility = item.subtitle.takeUnless { it.isNullOrEmpty() }?.let {
            binding.subtitle.text = it
            View.VISIBLE
        } ?: View.GONE
        binding.groupIsbn13.visibility = item.isbn13.takeUnless { it.isNullOrEmpty() }?.let {
            binding.isbn13.text = it
            View.VISIBLE
        } ?: View.GONE
        binding.groupPrice.visibility = item.price.takeUnless { it.isNullOrEmpty() }?.let {
            binding.price.text = it
            View.VISIBLE
        } ?: View.GONE
        binding.groupUrl.visibility = item.url.takeUnless { it.isNullOrEmpty() }?.let {
            binding.url.text = it
            View.VISIBLE
        } ?: View.GONE

        binding.root.setOnClickListener {
            context.startActivity(
                context.getIntent(
                    item.title,
                    item.subtitle,
                    item.isbn13,
                    item.price,
                    item.image,
                    item.url
                )
            )
        }
    }
}