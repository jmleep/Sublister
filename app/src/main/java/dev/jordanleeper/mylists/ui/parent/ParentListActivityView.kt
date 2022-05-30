package dev.jordanleeper.mylists.ui.parent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.dialog.AddEditListDialog
import dev.jordanleeper.mylists.ui.theme.MyListsTheme
import dev.jordanleeper.mylists.ui.theme.Palette
import dev.jordanleeper.mylists.ui.theme.getColor
import dev.jordanleeper.mylists.ui.theme.getPalette
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentListActivityView(id: Int, viewModel: ParentListActivityViewModel) {
    val parentList by viewModel.getParentList(id).observeAsState()
    val subLists by viewModel.getSubListsByParentId(id).observeAsState(initial = listOf())

    val itemTextColor = parentList?.textColor?.getColor() ?: Color.White

    val showAddListDialog = remember { mutableStateOf(false) }
    val palette = parentList?.color?.getPalette() ?: Palette()

    MyListsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = { showAddListDialog.value = true }) {
                            Icon(Icons.Default.Add, "Add Sublist", tint = itemTextColor)
                        }
                    },
                    title = {
                        Text(
                            parentList?.name ?: "Test",
                            fontSize = 30.sp,
                            color = itemTextColor
                        )
                    },
                    backgroundColor = parentList?.color?.getColor() ?: Color.Black
                )
            },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn() {
                        items(subLists, key = { it.hashCode() }) {
                            ParentListItem(
                                viewModel = viewModel,
                                parentList = parentList!!,
                                subList = it
                            )
                        }
                    }
                }
                AddEditListDialog(
                    showAddListDialog,
                    label = "Add SubList",
                    colors = palette.colors,
                    textColors = palette.textColors
                ) { newListName, newListColor, myTextColor ->
                    viewModel.addSubList(
                        SubList(
                            name = newListName,
                            color = newListColor,
                            textColor = myTextColor,
                            parentListId = parentList?.id ?: 0,
                            dateCreated = Date().time,
                            isComplete = false
                        )
                    )
                    showAddListDialog.value = false
                }
            })
    }

}