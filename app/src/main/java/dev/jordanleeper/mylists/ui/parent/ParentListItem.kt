package dev.jordanleeper.mylists.ui.parent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.jordanleeper.mylists.R
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.item.ItemAdapter
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.MarkCompleted
import dev.jordanleeper.mylists.ui.theme.getColor
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import java.util.*

@Composable
fun ParentListItem(
    viewModel: ParentListActivityViewModel,
    parentList: ParentList,
    subList: SubList
) {
    val isExpanded = remember { mutableStateOf(false) }
    val itemsList by viewModel.getItemsBySubListId(subList.id).observeAsState(initial = listOf())
    val itemText = remember { mutableStateOf("") }
    val haptic = LocalHapticFeedback.current
    val itemAdapter = ItemAdapter(itemsList)


    var temp = itemsList.toMutableStateList()

    val state = rememberReorderableLazyListState(onMove = { from, to ->
        var toIndex = to.index - 1
        if (to.index < 0) toIndex = 0
        if (to.index >= itemsList.size) toIndex = itemsList.size - 1

        Collections.swap(temp, from.index - 1, toIndex)
    }, onDragEnd = { from, to ->

        val updatedList = temp.mapIndexed { index, item ->
            val updateItem = item.copy()
            updateItem.position = index
            updateItem
        }
        println(updatedList)
        viewModel.updateItems(updatedList)
    })

    ListItemSwipeToDismiss(onSwipe = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            viewModel.deleteSubList(subList)
        }
        if (it == DismissValue.DismissedToEnd) {
            val updatedSubList = subList.copy()
            updatedSubList.isComplete = !subList.isComplete

            viewModel.updateSubList(updatedSubList)
            shouldDismiss = false
        }
        shouldDismiss
    }, isSwipingPrevented = isExpanded) {

        val textStyle = when (subList.isComplete) {
            true -> TextStyle(textDecoration = TextDecoration.LineThrough)
            false -> TextStyle(textDecoration = TextDecoration.None)
        }

        Box() {
            Column() {
                Box(
                    modifier = Modifier
                        .background(subList.color.getColor())
                        .fillMaxWidth()
                        .clickable {
                            isExpanded.value = !isExpanded.value
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            subList.name ?: "List",
                            color = subList.textColor.getColor(),
                            fontSize = 20.sp,
                            style = textStyle
                        )

                        if (subList.isComplete) {
                            Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                        } else {
                            NumberOfItemsChip(
                                isExpanded = isExpanded,
                                displayNumber = itemsList.filter { !it.isComplete }.size.toString(),
                                color = parentList.color.getColor(),
                                textColor = parentList.textColor.getColor()
                            )
                        }
                    }
                }


                AnimatedVisibility(
                    visible = isExpanded.value
                ) {
                    Column {


                        Row(
                            modifier = Modifier
                                .height(80.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(
                                    top = 5.dp,
                                    bottom = 5.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = itemText.value,
                                onValueChange = { itemText.value = it })
                            Button(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    val itemListPrevSize = itemsList.size
                                    viewModel.addItem(
                                        Item(
                                            name = itemText.value,
                                            subListId = subList.id,
                                            dateCreated = Date().time,
                                            position = itemsList.size + 1
                                        )
                                    )
                                    itemText.value = ""

                                }
                            ) {
                                Text("Add")
                            }
                        }

                        AndroidView(factory = {
                            val view = LayoutInflater.from(it)
                                .inflate(R.layout.item_recycler_view, null, false)


                            val recyclerView =
                                view.findViewById<RecyclerView>(R.id.item_recycler_view)

                            if (recyclerView.parent != null) {
                                (recyclerView.parent as ViewGroup).removeView(recyclerView)
                            }

                            recyclerView.adapter = ItemAdapter(itemsList)
                            recyclerView.layoutManager = LinearLayoutManager(it)
                            recyclerView
                        }, update = {

//                            if (itemsList.size !== it.adapter?.itemCount) {
//                                it.adapter.se
//                            }
                            
                            it.adapter = ItemAdapter(itemsList)
                        })
                    }

//                    val height = (itemsList.size * 60) + 80
//                    LazyColumn(
//                        modifier = Modifier
//                            .reorderable(state)
//                            .height(height.dp),
//                        state = state.listState
//                    ) {
//                        item {
//
//                        items(temp, key = { it.hashCode() }) { listItem ->
//                            ReorderableItem(state, key = listItem.hashCode()) { isDragging ->
//                                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
//
//                                Box(
//                                    modifier = Modifier
//                                        .shadow(elevation.value)
//                                        .detectReorderAfterLongPress(state)
//                                ) {
//                                    ItemListItem(item = listItem, viewModel)
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }
}