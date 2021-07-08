package com.example.sendbirdapp.ui.new

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.network.ItBookRepository

class NewViewModel : ViewModel() {

    private val repository = ItBookRepository()
    private val _newItemList = MutableLiveData<List<NewItem>>()
    val newItemList: LiveData<List<NewItem>> = _newItemList

    @MainThread
    fun setNewItemList(newItemList: List<NewItem>) {
        _newItemList.value = newItemList
    }

    suspend fun getNew() = repository.getNew()
}