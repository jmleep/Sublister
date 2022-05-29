package dev.jordanleeper.mylists.data

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

    fun getParentList(id: Int): LiveData<ParentList> {
        return repository.getParentListById(id)
    }

    fun getSubListsByParentId(id: Int): LiveData<List<SubList>> {
        return repository.getSubListsByParentId(id)
    }

    fun addSubList(list: SubList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSubList(list)
        }
    }

    fun updateSubList(list: SubList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubList(list)
        }
    }

    fun deleteSubList(list: SubList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSubList(list)
        }
    }
}