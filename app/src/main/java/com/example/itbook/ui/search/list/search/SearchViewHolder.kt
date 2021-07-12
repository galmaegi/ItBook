package com.example.itbook.ui.search.list.search

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.itbook.R
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.databinding.ItemLoadingBinding
import com.example.itbook.ui.detail.DetailActivity.Companion.getIntent
import com.example.itbook.ui.search.model.SearchBookItem
import com.example.itbook.ui.search.model.SearchItem
import com.example.itbook.ui.search.model.SearchLoadingItem

abstract class SearchViewHolder<VIEW : ViewBinding, ITEM : SearchItem>(
    protected val binding: VIEW
) : RecyclerView.ViewHolder(binding.root) {
    protected val context: Context = binding.root.context

    abstract fun onBind(item: ITEM)
}

class LoadingViewHolder(
    binding: ItemLoadingBinding
) : SearchViewHolder<ItemLoadingBinding, SearchLoadingItem>(binding) {
    override fun onBind(item: SearchLoadingItem) {
        // TODO: 2021/07/12 implement loading animation
    }
}

class SearchBookViewHolder(
    binding: ItemBookBinding
) : SearchViewHolder<ItemBookBinding, SearchBookItem>(binding) {

    override fun onBind(item: SearchBookItem) {
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