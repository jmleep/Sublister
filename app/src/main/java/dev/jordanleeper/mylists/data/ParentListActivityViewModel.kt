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

    fun getParentList(id: Int): LiveData<ParentListWithSubLists> {
        return repository.getParentListById(id)
    }

    fun updateParentList(list: ParentList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateParentList(list)
        }
    }
}