package dev.jordanleeper.mylists.data

import androidx.lifecycle.LiveData

class ListRepository(private val listDao: ListDao) {

    val readAllData: LiveData<List<ParentListWithSubLists>> = listDao.getAllLists()
    val readAllParentList: LiveData<List<ParentList>> = listDao.getAllParentLists()

    fun getParentListById(id: Int): LiveData<ParentList> {
        return listDao.getParentListById(id)
    }

    suspend fun getSubListsByParentId(id: Int): List<SubList> {
        return listDao.getSubListsByParentId(id)
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

    fun addSubList(list: SubList) {
        listDao.addSubList(list)
    }

    fun updateSubList(list: SubList) {
        listDao.updateSubList(list)
    }

    fun deleteSubList(list: SubList) {
        listDao.deleteSubList(list)
    }
}