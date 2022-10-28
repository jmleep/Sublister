package dev.jordanleeper.sublister.ui.listdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.jordanleeper.sublister.data.ParentListActivityViewModel
import dev.jordanleeper.sublister.data.SubList
import dev.jordanleeper.sublister.ui.dialog.AddEditListDialog
import dev.jordanleeper.sublister.ui.theme.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailView(id: Int, viewModel: ParentListActivityViewModel) {
    val parentList by viewModel.getParentList(id).observeAsState()
    val subLists by viewModel.getSubListsByParentId(id).observeAsState(initial = listOf())
    val showAddListDialog = remember { mutableStateOf(false) }
    val showEditListDialog = remember {
        mutableStateOf(false)
    }
    val currentlyEditingSubListName = remember {
        mutableStateOf("")
    }
    val currentlyEditingSublistColor = remember {
        mutableStateOf("")
    }
    var currentlyEditingSublist: SubList? = null
    val itemTextColor = parentList?.textColor?.getColor() ?: Color.White
    val palette = parentList?.color?.getPalette() ?: parentListPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = parentList?.color?.getColor() ?: MaterialTheme.colorScheme.primary
    )

    SublisterTheme {
        Scaffold(
            topBar = {
                ListDetailAppBar(showAddListDialog, itemTextColor, parentList)
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
                            ListDetailColumnItem(
                                viewModel = viewModel,
                                subList = it,
                                editList = {
                                    currentlyEditingSubListName.value = it.name ?: ""
                                    currentlyEditingSublistColor.value = it.color
                                    currentlyEditingSublist = it
                                    showEditListDialog.value = true
                                }
                            )
                        }
                    }
                }
                AddEditListDialog(
                    showAddListDialog,
                    label = "Add SubList",
                ) { newListName, newListColor, newTextColor ->
                    viewModel.addSubList(
                        SubList(
                            name = newListName,
                            color = newListColor,
                            textColor = newTextColor ?: White,
                            parentListId = parentList?.id ?: 0,
                            dateCreated = Date().time,
                            isComplete = false
                        )
                    )
                    showAddListDialog.value = false
                }
                AddEditListDialog(
                    showEditListDialog,
                    label = "Edit SubList",
                    currentName = currentlyEditingSubListName.value,
                    currentColor = currentlyEditingSublistColor.value,
                ) { newListName, newListColor, newTextColor ->
                    currentlyEditingSublist?.let { editingSubList ->
                        val editedList = editingSubList.copy(
                            name = newListName,
                            color = newListColor
                        )
                        newTextColor?.let {
                            editedList.textColor = it
                        }

                        viewModel.updateSubList(editedList)
                    }

                    showEditListDialog.value = false
                }
            })
    }

}