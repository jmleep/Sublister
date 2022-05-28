package dev.jordanleeper.mylists.data

import androidx.room.*

@Entity(tableName = "list")
data class ParentList(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String? = "",
    var color: Int? = 0,
    var isComplete: Boolean = false,
    @TypeConverters(Converters::class)
    var dateCreated: Long?
)

@Entity(
    tableName = "sublist",
    foreignKeys = [ForeignKey(
        entity = ParentList::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("parentList"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class SubList(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    var name: String? = "",
    @ColumnInfo(index = true)
    var parentList: String? = "",
    var isComplete: Boolean = false,
    var dateCreated: Long?
)

data class ParentListWithSubLists(
    @Embedded
    val parentList: ParentList,
    @Relation(parentColumn = "id", entityColumn = "parentList")
    val subLists: List<SubList>,
)

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    var name: String? = "",
    var isComplete: Boolean = false,
    var dateCreated: Long?
)

