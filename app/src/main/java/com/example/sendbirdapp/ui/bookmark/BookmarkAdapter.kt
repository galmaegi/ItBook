package com.example.sendbirdapp.ui.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sendbirdapp.R
import com.example.sendbirdapp.databinding.ItemBookBinding
import com.example.sendbirdapp.repository.db.model.BookmarkItem
import com.example.sendbirdapp.ui.detail.DetailActivity.Companion.getIntent

class BookmarkAdapter : ListAdapter<BookmarkItem, BookmarkViewHolder>(BookmarkDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

object BookmarkDiffCallback : DiffUtil.ItemCallback<BookmarkItem>() {
    override fun areItemsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean =
        oldItem.isbn13 == newItem.isbn13

    override fun areContentsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean =
        oldItem == newItem
}

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