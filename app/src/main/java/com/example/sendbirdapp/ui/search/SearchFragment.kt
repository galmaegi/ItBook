package com.example.sendbirdapp.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val adapter = SearchListAdapter()
        adapter.onListReachedEndListener = {
            fetchMoreSearchResult()
        }
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.searchButton.setOnClickListener {
            onSearchEvent(binding.searchText.text.toString())
        }

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            var handled = false;
            if ((actionId == EditorInfo.IME_ACTION_DONE) ||
                ((event.keyCode == KeyEvent.KEYCODE_ENTER) &&
                        (event.action == KeyEvent.ACTION_DOWN))
            ) {
                onSearchEvent(v.text.toString())
                handled = true
            }

            handled
        }

        searchViewModel.searchBookList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

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
        searchViewModel.requestSearch(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
