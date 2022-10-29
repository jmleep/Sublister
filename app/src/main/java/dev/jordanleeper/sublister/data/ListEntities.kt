package dev.jordanleeper.sublister.data

import androidx.room.*

@Entity(tableName = "list")
data class Sublist(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = "",
    var color: String = "#FFFFFF",
    var textColor: String = "#000000",
    var isComplete: Boolean,
    @TypeConverters(Converters::class)
    var dateCreated: Long?,
    var parentListId: Int? = -1
)

//@Entity(
//    tableName = "sublist",
//    foreignKeys = [ForeignKey(
//        entity = ParentList::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("parentListId"),
//        onDelete = ForeignKey.CASCADE
//    )]
//)
//data class SubList(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0,
//    @ColumnInfo(index = true)
//    var parentListId: Int = 0,
//    var name: String? = "",
//    var color: String = "#FFFFFF",
//    var textColor: String = "#000000",
//    var isComplete: Boolean,
//    var dateCreated: Long?
//)
//
//data class ParentListWithSubLists(
//    @Embedded
//    val parentList: ParentList,
//    @Relation(parentColumn = "id", entityColumn = "parentListId")
//    val subLists: List<SubList>,
//)S

@Entity(
    tableName = "item",
    foreignKeys = [ForeignKey(
        entity = Sublist::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("sublistId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val sublistId: Int? = 0,
    var name: String? = "",
    var isComplete: Boolean = false,
    var dateCreated: Long?,
    var position: Int? = 0
)



