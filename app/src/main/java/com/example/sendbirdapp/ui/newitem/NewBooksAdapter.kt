package com.example.sendbirdapp.ui.newitem

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