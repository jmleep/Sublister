package dev.jordanleeper.mylists.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(parentList: ParentList)

    @Query("SELECT * FROM list ORDER BY isComplete ASC, dateCreated DESC")
    fun getAllLists(): LiveData<List<ParentListWithSubLists>>

    @Query("SELECT * FROM list ORDER BY isComplete ASC, dateCreated DESC")
    fun getAllParentLists(): LiveData<List<ParentList>>

    @Delete
    fun deleteParentList(list: ParentList)

    @Update
    fun updateParentList(list: ParentList)

    @Query("SELECT * FROM list WHERE id = :id")
    fun getParentListById(id: Int): LiveData<ParentList>

    @Query("SELECT * FROM sublist WHERE parentListId = :id ORDER BY isComplete ASC, dateCreated DESC")
    suspend fun getSubListsByParentId(id: Int): List<SubList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubList(list: SubList)

    @Update
    fun updateSubList(list: SubList)

    @Delete
    fun deleteSubList(list: SubList)
}