package dev.jordanleeper.sublister.data

import androidx.lifecycle.LiveData

class ListRepository(private val listDao: ListDao) {

    val readAllData: LiveData<List<ParentListWithSubLists>> = listDao.getAllLists()

    fun getParentListById(id: Int): LiveData<ParentList> {
        return listDao.getParentListById(id)
    }

    fun getSubListsByParentId(id: Int): LiveData<List<SubList>> {
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

    fun getItemsBySubListId(id: Int): LiveData<List<Item>> {
        return listDao.getItemsBySubListId(id)
    }

    fun addItem(item: Item) {
        listDao.addItem(item)
    }

    fun deleteItem(item: Item) {
        listDao.deleteItem(item)
    }

    fun updateItem(updatedItem: Item) {
        listDao.updateItem(updatedItem)
    }

    fun updateItems(updatedList: List<Item>) {
        listDao.updateItems(updatedList)
    }
}