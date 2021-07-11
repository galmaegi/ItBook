package com.example.sendbirdapp.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.sendbirdapp.databinding.ItemBookBinding
import com.example.sendbirdapp.databinding.ItemLoadingBinding
import com.example.sendbirdapp.ui.search.model.SearchBookItem
import com.example.sendbirdapp.ui.search.model.SearchItem

class SearchListAdapter<VIEW : ViewBinding, ITEM : SearchItem> :
    ListAdapter<SearchItem, SearchViewHolder<VIEW, ITEM>>(SearchBooksDiffCallback) {
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_FOOTER = 2
    }

    var onListReachedEndListener: (() -> Unit) = { }

    // Workaround!! : There is an issue for using generic type in kotlin ListAdapter and ViewHolder
    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder<VIEW, ITEM> = when (viewType) {
        TYPE_FOOTER -> LoadingViewHolder(
            ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        else -> SearchBookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    } as SearchViewHolder<VIEW, ITEM>


    override fun onBindViewHolder(holder: SearchViewHolder<VIEW, ITEM>, position: Int) {
        if (currentList.size < 1) {
            return
        }

        if (holder is SearchBookViewHolder) {
            holder.onBind(getItem(position) as SearchBookItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SearchBookItem -> TYPE_NORMAL
            else -> TYPE_FOOTER
        }
    }

    override fun onViewAttachedToWindow(holder: SearchViewHolder<VIEW, ITEM>) {
        super.onViewAttachedToWindow(holder)
        if (holder is LoadingViewHolder) {
            onListReachedEndListener.invoke()
        }
    }

}

object SearchBooksDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
        when {
            oldItem is SearchBookItem && newItem is SearchBookItem -> oldItem.isbn13 == newItem.isbn13
            else -> false
        }


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
        when {
            oldItem is SearchBookItem && newItem is SearchBookItem -> oldItem == newItem
            else -> false
        }
}