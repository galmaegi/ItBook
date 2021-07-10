package com.example.sendbirdapp.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sendbirdapp.R
import com.example.sendbirdapp.databinding.ItemBookBinding
import com.example.sendbirdapp.databinding.ItemFooterBinding
import com.example.sendbirdapp.ui.detail.DetailActivity.Companion.getIntent
import com.example.sendbirdapp.ui.search.model.BookItem
import com.example.sendbirdapp.ui.search.model.SearchItem

class SearchListAdapter :
    ListAdapter<SearchItem, RecyclerView.ViewHolder>(SearchBooksDiffCallback) {
    companion object {
        const val TYPE_NORMAL = 1
        const val TYPE_FOOTER = 2
    }

    var onListReachedEndListener: (() -> Unit) = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("SearchListAdapter", "viewType $viewType")
        return when (viewType) {
            TYPE_FOOTER -> FooterViewHolder(
                ItemFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> SearchViewHolder(
                ItemBookBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (currentList.size < 1) {
            return
        }

        if (holder is SearchViewHolder) {
            holder.onBind(getItem(position) as BookItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BookItem -> TYPE_NORMAL
            else -> TYPE_FOOTER
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is FooterViewHolder) {
            onListReachedEndListener.invoke()
        }
    }

}

object SearchBooksDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
        when {
            oldItem is BookItem && newItem is BookItem -> oldItem.isbn13 == newItem.isbn13
            else -> false
        }


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
        when {
            oldItem is BookItem && newItem is BookItem -> oldItem == newItem
            else -> false
        }
}

class FooterViewHolder(
    private val binding: ItemFooterBinding
) : RecyclerView.ViewHolder(binding.root)


class SearchViewHolder(
    private val binding: ItemBookBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    fun onBind(item: BookItem) {
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
            context.startActivity(context.getIntent(item.isbn13))
        }
    }
}