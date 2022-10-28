package dev.jordanleeper.sublister.ui.listdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextDecoration
import dev.jordanleeper.sublister.data.ParentListActivityViewModel
import dev.jordanleeper.sublister.data.SubList
import dev.jordanleeper.sublister.ui.listitem.ListItem
import dev.jordanleeper.sublister.ui.listitem.NumberOfItemsChip
import dev.jordanleeper.sublister.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.sublister.ui.task.AddTask
import dev.jordanleeper.sublister.ui.task.TaskAdapter
import dev.jordanleeper.sublister.ui.task.TaskRecyclerView
import dev.jordanleeper.sublister.ui.theme.MarkCompleted
import dev.jordanleeper.sublister.ui.theme.getColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDetailColumnItem(
    viewModel: ParentListActivityViewModel,
    subList: SubList,
    editList: () -> Unit
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
            true -> MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
            false -> MaterialTheme.typography.titleMedium
        }

        Box(modifier = Modifier.combinedClickable(onClick = {
            isExpanded.value = !isExpanded.value
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }, onLongClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            editList()
        })) {
            Column() {
                ListItem(
                    color = subList.color.getColor(),
                    label = subList.name ?: "List",
                    style = textStyle
                ) {
                    if (subList.isComplete) {
                        Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                    } else {
                        NumberOfItemsChip(
                            isExpanded = isExpanded,
                            displayNumber = itemsList.filter { !it.isComplete }.size.toString(),
                            color = subList.color.getColor(),
                            textColor = subList.textColor.getColor()
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpanded.value
                ) {
                    Column {
                        AddTask(
                            text = itemText,
                            viewModel = viewModel,
                            subList = subList,
                            itemsList = itemsList
                        )
                        TaskRecyclerView(
                            items = itemsList,
                            taskAdapter = TaskAdapter(
                                itemsList,
                                subList,
                                viewModel,
                                MaterialTheme.colorScheme
                            )
                        )
                    }
                }
            }
        }
    }
}