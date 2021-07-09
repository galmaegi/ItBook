package com.example.sendbirdapp.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sendbirdapp.common.VerticalSpaceItemDecoration
import com.example.sendbirdapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val searchListAdapter = SearchListAdapter()
        searchListAdapter.onListReachedEndListener = {
            fetchMoreSearchResult()
        }
        binding.searchList.adapter = searchListAdapter
        val searchLayoutManager = LinearLayoutManager(context)
        binding.searchList.layoutManager = searchLayoutManager
        binding.searchList.addItemDecoration(
            DividerItemDecoration(
                context,
                searchLayoutManager.orientation
            )
        )
        binding.searchList.addItemDecoration(VerticalSpaceItemDecoration.DEFAULT_DECORATION)

        val historyListAdapter = HistoryListAdapter(searchViewModel)
        binding.historyList.adapter = historyListAdapter
        val historyLayoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.HORIZONTAL
        }
        binding.historyList.layoutManager = historyLayoutManager

        binding.searchButton.setOnClickListener {
            onSearchEvent(binding.searchText.text.toString())
        }

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                ((event.keyCode == KeyEvent.KEYCODE_ENTER) &&
                        (event.action == KeyEvent.ACTION_DOWN))
            ) {
                onSearchEvent(v.text.toString())
                handled = true
            }

            handled
        }

        searchViewModel.historyList.observe(viewLifecycleOwner) {
            historyListAdapter.submitList(it.reversed())
        }

        searchViewModel.searchBookList.observe(viewLifecycleOwner) {
            searchListAdapter.submitList(it)
        }

        searchViewModel.searchText.observe(viewLifecycleOwner) {
            searchViewModel.addHistory(it)
            searchViewModel.requestSearch(it)
            binding.searchText.setText(it)
        }

        return binding.root
    }

    private fun fetchMoreSearchResult() {
        searchViewModel.searchModel?.let {
            if (it.isEnd) {
                return@let
            }
            searchViewModel.requestSearch(it.query, it.currentPage + 1, isSearch = false)
        }
    }

    private fun onSearchEvent(query: String) {
        searchViewModel.addHistory(query)
        searchViewModel.requestSearch(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
