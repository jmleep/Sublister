package dev.jordanleeper.mylists.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.ui.button.AddListFloatingActionButton
import dev.jordanleeper.mylists.ui.dialog.AddListDialog
import dev.jordanleeper.mylists.ui.list.ParentListItem
import dev.jordanleeper.mylists.ui.theme.MyListsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityView(viewModel: ListViewModel) {
    val context = LocalContext.current
    val parentLists by viewModel.readAllData.observeAsState(initial = listOf())
    val showAddListDialog = remember { mutableStateOf(false) }

    MyListsTheme {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("My Lists") },
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
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
                AddListDialog(showAddListDialog, viewModel)
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                ) {
                    items(parentLists, key = { it.parentList.id }) { it ->
                        ParentListItem(item = it, viewModel)
                    }
                }
            }
        }, containerColor = MaterialTheme.colorScheme.background)
    }
}