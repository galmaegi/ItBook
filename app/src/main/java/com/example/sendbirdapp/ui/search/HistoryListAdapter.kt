package com.example.sendbirdapp.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sendbirdapp.databinding.ItemHistoryBinding

class HistoryListAdapter(
    private val historyControl: HistoryControl
) : ListAdapter<String, HistoryItemViewHolder>(HistoryDiffCallback) {
    private var recyclerView: RecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder =
        HistoryItemViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            historyControl
        )

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCurrentListChanged(
        previousList: MutableList<String>,
        currentList: MutableList<String>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        recyclerView?.scrollToPosition(0)
    }
}

object HistoryDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}

class HistoryItemViewHolder(
    private val binding: ItemHistoryBinding,
    private val historyControl: HistoryControl
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(history: String) {
        binding.historyText.text = history
        binding.root.setOnClickListener {
            historyControl.onSelectHistory(history)
        }
        binding.deleteButton.setOnClickListener {
            historyControl.removeHistory(history)
        }
    }
}
