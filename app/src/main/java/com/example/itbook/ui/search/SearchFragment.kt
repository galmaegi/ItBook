package com.example.itbook.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.itbook.common.BOOK_SPACE_DECORATION
import com.example.itbook.common.HISTORY_SPACE_DECORATION
import com.example.itbook.databinding.FragmentSearchBinding
import com.example.itbook.repository.db.model.SearchHistory
import com.example.itbook.ui.search.list.history.HistoryListAdapter
import com.example.itbook.ui.search.list.search.SearchListAdapter
import com.example.itbook.ui.search.model.SearchItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchListAdapter = SearchListAdapter<ViewBinding, SearchItem>()
    private val historyListAdapter = HistoryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initUi()
        initObserver()
        return binding.root
    }

    private fun initUi() {
        with(binding.searchList) {
            adapter = searchListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(BOOK_SPACE_DECORATION)
            searchListAdapter.onListReachedEndListener = {
                fetchMoreSearchResult()
            }
        }

        with(binding.historyList) {
            adapter = historyListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = RecyclerView.HORIZONTAL
            }
            addItemDecoration(HISTORY_SPACE_DECORATION)
            historyListAdapter.historyListEventListener = object :
                HistoryListAdapter.HistoryListEventListener {
                override fun onSelectHistory(query: SearchHistory) {
                    searchViewModel.onSelectHistory(query)
                }

                override fun removeHistory(query: SearchHistory) {
                    searchViewModel.removeHistory(query)
                }
            }
        }


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
    }

    private fun initObserver() {
        searchViewModel.historyList.observe(viewLifecycleOwner) { searchHistorySet ->
            historyListAdapter.submitList(searchHistorySet.sortedByDescending {
                it.lastAccessedTime
            })
        }

        searchViewModel.searchBookList.observe(viewLifecycleOwner) {
            searchListAdapter.submitList(it)
        }

        searchViewModel.searchText.observe(viewLifecycleOwner) {
            searchViewModel.addHistory(it)
            searchViewModel.requestSearch(it)
            binding.searchText.setText(it)
        }
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
