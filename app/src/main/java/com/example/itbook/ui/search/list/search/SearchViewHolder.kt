package com.example.itbook.ui.search.list.search

import android.app.ActivityOptions
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.itbook.common.extensions.getActivityContext
import com.example.itbook.common.extensions.getCompressedRawBitmap
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.databinding.ItemLoadingBinding
import com.example.itbook.ui.detail.DetailActivity.Companion.getDetailActivityIntent
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
        binding.bookItem = item
        binding.root.setOnClickListener {
            val options = context.getActivityContext()?.let {
                ActivityOptions.makeSceneTransitionAnimation(
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
                    binding.image.getCompressedRawBitmap()
                ),
                options?.toBundle()
            )
        }
    }
}