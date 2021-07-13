package com.example.itbook.ui.search.list.history

import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.databinding.ItemHistoryBinding
import com.example.itbook.ui.search.model.HistoryItem

class HistoryItemViewHolder(
    private val binding: ItemHistoryBinding,
    private val historyControl: HistoryListAdapter.HistoryListEventListener
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HistoryItem) {
        binding.historyItem = item
        binding.root.setOnClickListener {
            historyControl.onSelectHistory(item)
        }
        binding.deleteButton.setOnClickListener {
            historyControl.removeHistory(item)
        }
    }
}