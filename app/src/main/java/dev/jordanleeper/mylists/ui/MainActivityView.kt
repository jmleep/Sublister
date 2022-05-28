package dev.jordanleeper.mylists.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.ui.button.AddListFloatingActionButton
import dev.jordanleeper.mylists.ui.list.ParentListItem
import dev.jordanleeper.mylists.ui.theme.MyListsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityView(viewModel: ListViewModel) {
    val context = LocalContext.current
    val list by viewModel.readAllData.observeAsState(initial = listOf())

    val sortedList = list.sortedBy { it.parentList.isComplete }

    MyListsTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("My Lists") },
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        }, floatingActionButton = {
            AddListFloatingActionButton {
                viewModel.addList(ParentList(0, "test list", Color.Yellow.toArgb(), false))
                Toast.makeText(context, "clicked add list", Toast.LENGTH_LONG).show()
            }
        }, content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                ) {
                    items(sortedList, key = { it.parentList.id }) { it ->
                        ParentListItem(item = it, viewModel)
                    }
                }
            }
        }, containerColor = MaterialTheme.colorScheme.background)
    }
}