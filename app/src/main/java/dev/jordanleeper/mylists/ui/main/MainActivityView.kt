package dev.jordanleeper.mylists.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.MainActivityViewModel
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.ui.button.AddListFloatingActionButton
import dev.jordanleeper.mylists.ui.dialog.AddListDialog
import dev.jordanleeper.mylists.ui.theme.MyListsTheme
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityView(viewModel: MainActivityViewModel) {
    val parentLists by viewModel.readAllData.observeAsState(initial = listOf())
    val showAddListDialog = remember { mutableStateOf(false) }

    MyListsTheme {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) { Text("My Lists", fontSize = 30.sp) }
                },
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }, floatingActionButton = {
            AddListFloatingActionButton(showAddListDialog)
        }, content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                AddListDialog(
                    showAddListDialog,
                ) { newListName, newListColor, myTextColor ->
                    viewModel.addList(
                        ParentList(
                            0,
                            newListName,
                            newListColor,
                            myTextColor,
                            false,
                            Date().time
                        )
                    )
                    showAddListDialog.value = false
                }
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                ) {
                    items(parentLists, key = { it.parentList.id }) { it ->
                        MainListItem(item = it, viewModel)
                    }
                }
            }
        }, containerColor = MaterialTheme.colorScheme.background)
    }
}