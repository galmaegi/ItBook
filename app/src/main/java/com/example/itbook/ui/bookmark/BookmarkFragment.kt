package com.example.itbook.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itbook.R
import com.example.itbook.common.BOOK_SPACE_DECORATION
import com.example.itbook.databinding.FragmentBookmarkBinding
import com.example.itbook.repository.db.model.BookmarkItem
import com.example.itbook.ui.bookmark.list.BookmarkAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter: BookmarkAdapter by lazy {
        BookmarkAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        binding.list.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.list.layoutManager = layoutManager
        binding.list.addItemDecoration(BOOK_SPACE_DECORATION)

        bookmarkViewModel.bookmarkList.observe(viewLifecycleOwner, { list ->
            bookmarkViewModel.bookmarkSortMode.value?.let { mode ->
                refreshListItems(list, mode)
            }
        })
        bookmarkViewModel.bookmarkSortMode.observe(viewLifecycleOwner, { mode ->
            bookmarkViewModel.bookmarkList.value?.let { list ->
                refreshListItems(list, mode)
            }
        })
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bookmark_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark_sort -> {
                sortListEvent()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refreshListItems(list: List<BookmarkItem>, mode: BookmarkSortMode) {
        adapter.submitList(list.sortedWith(mode.comparator))
    }

    private fun sortListEvent() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sort")
        builder.setItems(SORT_DIALOG_MENU) { dialog, which ->
            BookmarkSortMode.values().firstOrNull {
                it.desc == SORT_DIALOG_MENU[which]
            }?.let {
                bookmarkViewModel.setBookmarkSortMode(it)
            }
        }
        builder.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val SORT_DIALOG_MENU: Array<String> = BookmarkSortMode.values().map {
            it.desc
        }.toTypedArray()
    }
}