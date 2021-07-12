package com.example.itbook.ui.newitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itbook.common.BOOK_SPACE_DECORATION
import com.example.itbook.databinding.FragmentNewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : Fragment() {

    private val newViewModel: NewViewModel by viewModels()
    private var _binding: FragmentNewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBinding.inflate(inflater, container, false)

        val adapter = NewBooksAdapter()
        binding.list.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.list.layoutManager = layoutManager
        binding.list.addItemDecoration(BOOK_SPACE_DECORATION)

        newViewModel.newItemList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newViewModel.fetchNewItem()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}