package com.example.itbook.ui.newitem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itbook.ui.newitem.model.NewItem
import com.example.itbookapi.ItBookRepository
import com.example.itbookapi.network.model.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { context, throwable ->
            Log.d("NewViewModel", throwable.message.toString())
        }) {
            itBookRepository.getNew().collect { response ->
                _newItemList.postValue(response.books.map { it.toNewItem() })
            }
        }
    }

    private fun BookItem.toNewItem() = NewItem(title, subtitle, isbn13, price, image, url)
}