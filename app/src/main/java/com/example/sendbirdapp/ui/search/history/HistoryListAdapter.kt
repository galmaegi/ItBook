package com.example.sendbirdapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sendbirdapp.databinding.ItemHistoryBinding
import com.example.sendbirdapp.repository.db.model.SearchHistory

class HistoryListAdapter : ListAdapter<SearchHistory, HistoryItemViewHolder>(HistoryDiffCallback) {
    var historyListEventListener: HistoryListEventListener? = null

    private var recyclerView: RecyclerView? = null
    private val _innerHistoryListEventListener = object : HistoryListEventListener {
        override fun onSelectHistory(query: SearchHistory) {
            historyListEventListener?.onSelectHistory(query)
        }

        override fun removeHistory(query: SearchHistory) {
            historyListEventListener?.removeHistory(query)
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
        previousList: MutableList<SearchHistory>,
        currentList: MutableList<SearchHistory>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        recyclerView?.scrollToPosition(0)
    }
}

object HistoryDiffCallback : DiffUtil.ItemCallback<SearchHistory>() {
    override fun areItemsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean =
        oldItem.query == newItem.query

    override fun areContentsTheSame(oldItem: SearchHistory, newItem: SearchHistory): Boolean =
        oldItem == newItem
}

interface HistoryListEventListener {
    fun onSelectHistory(query: SearchHistory)
    fun removeHistory(query: SearchHistory)
}

class HistoryItemViewHolder(
    private val binding: ItemHistoryBinding,
    private val historyControl: HistoryListEventListener
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(history: SearchHistory) {
        binding.historyText.text = history.query
        binding.root.setOnClickListener {
            historyControl.onSelectHistory(history)
        }
        binding.deleteButton.setOnClickListener {
            historyControl.removeHistory(history)
        }
    }
}
