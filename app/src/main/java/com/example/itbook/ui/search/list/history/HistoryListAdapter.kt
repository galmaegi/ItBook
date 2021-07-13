package com.example.itbook.ui.search.list.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.databinding.ItemHistoryBinding
import com.example.itbook.ui.search.model.HistoryItem

class HistoryListAdapter : ListAdapter<HistoryItem, HistoryItemViewHolder>(HistoryDiffCallback) {
    var historyListEventListener: HistoryListEventListener? = null

    private var recyclerView: RecyclerView? = null
    private val _innerHistoryListEventListener = object : HistoryListEventListener {
        override fun onSelectHistory(item: HistoryItem) {
            historyListEventListener?.onSelectHistory(item)
        }

        override fun removeHistory(item: HistoryItem) {
            historyListEventListener?.removeHistory(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder =
        HistoryItemViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            _innerHistoryListEventListener
        )

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCurrentListChanged(
        previousList: MutableList<HistoryItem>,
        currentList: MutableList<HistoryItem>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        recyclerView?.scrollToPosition(0)
    }

    object HistoryDiffCallback : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean =
            oldItem.keyword == newItem.keyword

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean =
            oldItem == newItem
    }

    interface HistoryListEventListener {
        fun onSelectHistory(item: HistoryItem)
        fun removeHistory(item: HistoryItem)
    }
}
