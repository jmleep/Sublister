package dev.jordanleeper.mylists.ui.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.item.ItemListItem
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.MarkCompleted
import dev.jordanleeper.mylists.ui.theme.getColor
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


                if (isExpanded.value) {
                    val height = (itemsList.size * 60) + 80
                    LazyColumn(modifier = Modifier.height(height.dp)) {
                        item {
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
                                        viewModel.addItem(
                                            Item(
                                                name = itemText.value,
                                                subListId = subList.id,
                                                dateCreated = Date().time
                                            )
                                        )
                                        itemText.value = ""
                                    }

                                ) {
                                    Text("Add")
                                }
                            }
                        }
                        items(itemsList, key = { it.hashCode() }) {
                            Row() {
                                ItemListItem(item = it, viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}