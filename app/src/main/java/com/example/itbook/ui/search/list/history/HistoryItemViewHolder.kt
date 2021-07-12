package com.example.itbook.ui.search.list.history

import androidx.recyclerview.widget.RecyclerView
import com.example.itbook.databinding.ItemHistoryBinding
import com.example.itbook.repository.db.model.SearchHistory

class HistoryItemViewHolder(
    private val binding: ItemHistoryBinding,
    private val historyControl: HistoryListAdapter.HistoryListEventListener
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(history: SearchHistory) {
        binding.historyText.text = history.keyword
        binding.root.setOnClickListener {
            historyControl.onSelectHistory(history)
        }
        binding.deleteButton.setOnClickListener {
            historyControl.removeHistory(history)
        }
    }
}