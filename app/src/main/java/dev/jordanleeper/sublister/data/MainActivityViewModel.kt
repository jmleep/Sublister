package dev.jordanleeper.sublister.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Sublist>>
    private val repository: ListRepository


    init {
        val listDao = AppDatabase.getDatabase(application).listDao()
        repository = ListRepository(listDao)
        readAllData = repository.readAllData
    }

    fun addSublist(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSublist(list)
        }
    }

    fun deleteList(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            val childLists = repository.getChildSublistsByParentSublistId(list.id).value ?: listOf()

            for (childList in childLists) {
                if (childList.id != -1)
                    deleteList(childList)
            }

            repository.deleteSubList(list)

        }
    }

    fun updateSubList(list: Sublist) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSubList(list)
        }
    }
}