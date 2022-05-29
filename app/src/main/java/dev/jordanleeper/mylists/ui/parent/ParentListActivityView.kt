package dev.jordanleeper.mylists.ui.parent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import dev.jordanleeper.mylists.ui.dialog.AddListDialog
import dev.jordanleeper.mylists.ui.theme.MyListsTheme
import dev.jordanleeper.mylists.ui.theme.getColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentListActivityView(id: Int, viewModel: ParentListActivityViewModel) {
    val item by viewModel.getParentList(id).observeAsState()
    val itemTextColor = item?.parentList?.textColor?.getColor() ?: Color.White

    val showAddListDialog = remember { mutableStateOf(false) }

    MyListsTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    actions = {
                        IconButton(onClick = { println("test") }) {
                            Icon(Icons.Default.Add, "Add Sublist", tint = itemTextColor)
                        }
                    },
                    title = {
                        Text(
                            item?.parentList?.name ?: "Test",
                            fontSize = 30.sp,
                            color = itemTextColor
                        )
                    },
                    backgroundColor = item?.parentList?.color?.getColor() ?: Color.Black
                )
            },
            content = { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Text("ASDF")
                }
                AddListDialog(
                    showAddListDialog,
                ) { newListName, newListColor, myTextColor ->
                    println("test")
                    showAddListDialog.value = false
                }
            })
    }

}