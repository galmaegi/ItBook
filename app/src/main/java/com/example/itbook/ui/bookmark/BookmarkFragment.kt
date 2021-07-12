package com.example.itbook.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itbook.common.BOOK_SPACE_DECORATION
import com.example.itbook.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        val adapter = BookmarkAdapter()
        binding.list.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.list.layoutManager = layoutManager
        binding.list.addItemDecoration(BOOK_SPACE_DECORATION)

        bookmarkViewModel.bookmarkList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}