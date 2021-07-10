package com.example.sendbirdapp.ui.newitem

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sendbirdapp.R
import com.example.sendbirdapp.databinding.ItemBookBinding
import com.example.sendbirdapp.ui.detail.DetailActivity.Companion.getIntent

class NewBooksAdapter : ListAdapter<NewItem, NewBooksViewHolder>(NewBooksDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewBooksViewHolder {
        return NewBooksViewHolder(
            ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewBooksViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

object NewBooksDiffCallback : DiffUtil.ItemCallback<NewItem>() {
    override fun areItemsTheSame(oldItem: NewItem, newItem: NewItem): Boolean =
        oldItem.isbn13 == newItem.isbn13

    override fun areContentsTheSame(oldItem: NewItem, newItem: NewItem): Boolean =
        oldItem == newItem
}

class NewBooksViewHolder(
    private val binding: ItemBookBinding
) : RecyclerView.ViewHolder(binding.root) {
    private val context: Context = binding.root.context

    fun onBind(item: NewItem) {
        binding.title.text = item.title
        binding.subtitle.text = item.subtitle
        binding.isbn13.text = item.isbn13
        binding.price.text = item.price
        binding.url.text = item.url
        Glide.with(context)
            .load(item.image)
            .fitCenter()
            .placeholder(R.drawable.loading_example)
            .into(binding.image)

        binding.root.setOnClickListener {
            context.startActivity(context.getIntent(item.isbn13))
        }
    }
}