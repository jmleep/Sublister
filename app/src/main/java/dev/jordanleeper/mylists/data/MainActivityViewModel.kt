package dev.jordanleeper.mylists.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<ParentListWithSubLists>>
    private val repository: ListRepository


    init {
        val listDao = AppDatabase.getDatabase(application).listDao()
        repository = ListRepository(listDao)
        readAllData = repository.readAllData
    }

    fun addList(list: ParentList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addList(list)
        }
    }

    fun deleteList(list: ParentList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteParentList(list)
        }
    }

    fun updateParentList(list: ParentList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateParentList(list)
        }
    }
}