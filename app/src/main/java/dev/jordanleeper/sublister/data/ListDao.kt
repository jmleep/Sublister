package dev.jordanleeper.sublister.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun addList(parentList: ParentList)
//
//    @Query("SELECT * FROM list ORDER BY dateCreated DESC")
//    fun getAllLists(): LiveData<List<ParentListWithSubLists>>
//
//    @Query("SELECT * FROM list ORDER BY dateCreated DESC")
//    fun getAllParentLists(): LiveData<List<ParentList>>
//
//    @Delete
//    fun deleteParentList(list: ParentList)
//
//    @Update
//    fun updateParentList(list: ParentList)
//
//    @Query("SELECT * FROM list WHERE id = :id")
//    fun getParentListById(id: Int): LiveData<ParentList>

    @Query("SELECT * FROM list WHERE id = :id")
    fun getSublistById(id: Int): LiveData<Sublist>

    @Query("SELECT * FROM list WHERE parentListId = -1 ORDER BY dateCreated DESC")
    fun getAllRootSublists(): LiveData<List<Sublist>>

    @Query("SELECT * FROM list WHERE parentListId = :id ORDER BY dateCreated DESC")
    fun getChildSublistsByParentSublistId(id: Int): LiveData<List<Sublist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSublist(list: Sublist)

    @Update
    fun updateSubList(list: Sublist)

    @Delete
    fun deleteSubList(list: Sublist)

    @Query("SELECT * FROM item WHERE sublistId = :id ORDER BY position ASC")
    fun getItemsBySublistId(id: Int): LiveData<List<Item>>

    @Insert
    fun addItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(updatedItem: Item)

    @Update
    fun updateItems(updatedList: List<Item>)
}