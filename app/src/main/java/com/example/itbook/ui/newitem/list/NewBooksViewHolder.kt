package com.example.itbook.ui.newitem.list

import android.app.ActivityOptions
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.common.extensions.getActivityContext
import com.example.itbook.common.extensions.getCompressedRawBitmap
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.ui.detail.DetailActivity.Companion.getDetailActivityIntent
import com.example.itbook.ui.newitem.model.NewItem

class NewBooksViewHolder(
    private val binding: ItemBookBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    fun onBind(item: NewItem) {
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