package com.example.sendbirdapp.ui.newitem

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sendbirdapp.repository.ItBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val itBookRepository: ItBookRepository
) : ViewModel() {

    private val _newItemList = MutableLiveData<List<NewItem>>()
    val newItemList: LiveData<List<NewItem>> = _newItemList

    @MainThread
    fun setNewItemList(newItemList: List<NewItem>) {
        _newItemList.value = newItemList
    }

    suspend fun getNew() = itBookRepository.getNew()
}