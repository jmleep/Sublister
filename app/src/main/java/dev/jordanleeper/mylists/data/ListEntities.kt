package dev.jordanleeper.mylists.data

import androidx.room.*

@Entity(tableName = "list")
data class ParentList(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String? = "",
    var color: String = "#FFFFFF",
    var textColor: String = "#000000",
    var isComplete: Boolean,
    @TypeConverters(Converters::class)
    var dateCreated: Long?
)

@Entity(
    tableName = "sublist",
    foreignKeys = [ForeignKey(
        entity = ParentList::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parentListId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    var parentListId: Int = 0,
    var name: String? = "",
    var color: String = "#FFFFFF",
    var textColor: String = "#000000",
    var isComplete: Boolean,
    var dateCreated: Long?
)

data class ParentListWithSubLists(
    @Embedded
    val parentList: ParentList,
    @Relation(parentColumn = "id", entityColumn = "parentListId")
    val subLists: List<SubList>,
)

@Entity(
    tableName = "item",
    foreignKeys = [ForeignKey(
        entity = SubList::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("subListId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(index = true)
    val subListId: Int? = 0,
    var name: String? = "",
    var isComplete: Boolean = false,
    var dateCreated: Long?
)

data class SubListWithItems(
    @Embedded
    val subList: SubList,
    @Relation(parentColumn = "id", entityColumn = "subListId")
    val items: List<Item>,
)


