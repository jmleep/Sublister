package dev.jordanleeper.sublister.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(parentList: ParentList)

    @Query("SELECT * FROM list ORDER BY dateCreated DESC")
    fun getAllLists(): LiveData<List<ParentListWithSubLists>>

    @Query("SELECT * FROM list ORDER BY dateCreated DESC")
    fun getAllParentLists(): LiveData<List<ParentList>>

    @Delete
    fun deleteParentList(list: ParentList)

    @Update
    fun updateParentList(list: ParentList)

    @Query("SELECT * FROM list WHERE id = :id")
    fun getParentListById(id: Int): LiveData<ParentList>

    @Query("SELECT * FROM sublist WHERE parentListId = :id ORDER BY dateCreated DESC")
    fun getSubListsByParentId(id: Int): LiveData<List<SubList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubList(list: SubList)

    @Update
    fun updateSubList(list: SubList)

    @Delete
    fun deleteSubList(list: SubList)

    @Query("SELECT * FROM item WHERE subListId = :id ORDER BY position ASC")
    fun getItemsBySubListId(id: Int): LiveData<List<Item>>

    @Insert
    fun addItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(updatedItem: Item)

    @Update
    fun updateItems(updatedList: List<Item>)
}