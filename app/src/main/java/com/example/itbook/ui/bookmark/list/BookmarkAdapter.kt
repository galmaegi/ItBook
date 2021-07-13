package com.example.itbook.ui.bookmark.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.ui.bookmark.model.BookmarkItem

class BookmarkAdapter : ListAdapter<BookmarkItem, BookmarkViewHolder>(BookmarkDiffCallback) {
    private var recyclerView: RecyclerView? = null

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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCurrentListChanged(
        previousList: MutableList<BookmarkItem>,
        currentList: MutableList<BookmarkItem>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        recyclerView?.scrollToPosition(0)
    }

    object BookmarkDiffCallback : DiffUtil.ItemCallback<BookmarkItem>() {
        override fun areItemsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean =
            oldItem.isbn13 == newItem.isbn13

        override fun areContentsTheSame(oldItem: BookmarkItem, newItem: BookmarkItem): Boolean =
            oldItem == newItem
    }
}
