package dev.jordanleeper.sublister.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParentListActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ListRepository

    init {
        val listDao = AppDatabase.getDatabase(application).listDao()
        repository = ListRepository(listDao)
    }

    fun getSublist(id: Int): LiveData<Sublist> {
        return repository.getSublistById(id);
    }

    fun getChildSublistsByParentSublistId(id: Int): LiveData<List<Sublist>> {
        return repository.getChildSublistsByParentSublistId(id)
    }

    fun addSubList(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSublist(list)
        }
    }

    fun updateSubList(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubList(list)
        }
    }

    fun deleteSubList(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubList(list)
        }
    }

    fun getItemsBySubListId(id: Int): LiveData<List<Item>> {
        return repository.getItemsBySubListId(id)
    }

    fun addItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }

    fun updateItem(updatedItem: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(updatedItem)
        }
    }

    fun updateItems(updatedList: List<Item>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItems(updatedList)
        }
    }
}