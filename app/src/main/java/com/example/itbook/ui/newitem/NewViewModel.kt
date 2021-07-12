package com.example.itbook.ui.newitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itbook.repository.ItBookRepository
import com.example.itbook.repository.network.model.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ViewModel() {

    private val _newItemList = MutableLiveData<List<NewItem>>()
    val newItemList: LiveData<List<NewItem>> = _newItemList

    fun fetchNewItem() {
        CoroutineScope(Dispatchers.IO).launch {
            itBookRepository.getNew().collect { response ->
                _newItemList.postValue(response.books.map { it.toNewItem() })
            }
        }
    }

    private fun BookItem.toNewItem() = NewItem(title, subtitle, isbn13, price, image, url)
}