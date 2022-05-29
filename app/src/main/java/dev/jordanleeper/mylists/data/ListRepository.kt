package dev.jordanleeper.mylists.data

import androidx.lifecycle.LiveData

class ListRepository(private val listDao: ListDao) {

    val readAllData: LiveData<List<ParentListWithSubLists>> = listDao.getAllLists()

    fun getParentListById(id: Int): LiveData<ParentListWithSubLists> {
        return listDao.getParentListById(id)
    }

    fun addList(parentList: ParentList) {
        listDao.addList(parentList)
    }

    fun deleteParentList(parentList: ParentList) {
        listDao.deleteParentList(parentList)
    }

    fun updateParentList(parentList: ParentList) {
        listDao.updateParentList(parentList)
    }
}