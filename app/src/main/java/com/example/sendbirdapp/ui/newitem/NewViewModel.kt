package com.example.sendbirdapp.ui.newitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.repository.ItBookRepository
import com.example.sendbirdapp.repository.network.model.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                withContext(Dispatchers.Main) {
                    _newItemList.value = response.books.map { it.toNewItem() }
                }
            }
        }
    }

    private fun BookItem.toNewItem() = NewItem(title, subtitle, isbn13, price, image, url)
}