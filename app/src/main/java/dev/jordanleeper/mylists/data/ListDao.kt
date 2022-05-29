package dev.jordanleeper.mylists.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addList(parentList: ParentList)

    @Query("SELECT * FROM list ORDER BY isComplete ASC, dateCreated DESC")
    fun getAllLists(): LiveData<List<ParentListWithSubLists>>

    @Delete
    fun deleteParentList(list: ParentList)

    @Update
    fun updateParentList(list: ParentList)

    @Query("SELECT * FROM list WHERE id = :id")
    fun getParentListById(id: Int): LiveData<ParentListWithSubLists>
}