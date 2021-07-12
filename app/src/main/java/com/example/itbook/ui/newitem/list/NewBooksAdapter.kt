package com.example.itbook.ui.newitem.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.itbook.databinding.ItemBookBinding
import com.example.itbook.ui.newitem.model.NewItem

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

    object NewBooksDiffCallback : DiffUtil.ItemCallback<NewItem>() {
        override fun areItemsTheSame(oldItem: NewItem, newItem: NewItem): Boolean =
            oldItem.isbn13 == newItem.isbn13

        override fun areContentsTheSame(oldItem: NewItem, newItem: NewItem): Boolean =
            oldItem == newItem
    }
}