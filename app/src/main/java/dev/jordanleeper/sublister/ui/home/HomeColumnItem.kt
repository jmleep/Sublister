package dev.jordanleeper.sublister.ui.home

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextDecoration
import dev.jordanleeper.sublister.data.MainActivityViewModel
import dev.jordanleeper.sublister.data.Sublist
import dev.jordanleeper.sublister.ui.dialog.AddEditListDialog
import dev.jordanleeper.sublister.ui.listdetail.ListDetailActivity
import dev.jordanleeper.sublister.ui.listitem.ListItem
import dev.jordanleeper.sublister.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.sublister.ui.theme.MarkCompleted
import dev.jordanleeper.sublister.ui.theme.getColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeColumnItem(
    sublist: Sublist,
    viewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val showEditListDialog = remember { mutableStateOf(false) }

    ListItemSwipeToDismiss(onSwipe = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            viewModel.deleteList(sublist)
        }
        if (it == DismissValue.DismissedToEnd) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            val newList = sublist.copy()
            newList.isComplete = !newList.isComplete

            viewModel.updateSubList(newList)
            shouldDismiss = false
        }
        shouldDismiss
    }) {
        var textStyle = when (sublist.isComplete) {
            true -> MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.LineThrough)
            false -> MaterialTheme.typography.titleLarge
        }

//        if (parentListWithSubLists.subLists.isNotEmpty()) {
//            textStyle = textStyle.copy(fontWeight = FontWeight.Bold)
//        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)//parentListWithSubLists.parentList.color.getColor())
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        val intent = Intent(
                            context,
                            ListDetailActivity::class.java
                        )
                        //intent.putExtra("parentListId", parentListWithSubLists.parentList.id);
                        //context.startActivity(intent)
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showEditListDialog.value = true
                    }
                )
        ) {
            ListItem(
                color = sublist.color.getColor(),
                label = sublist.name ?: "List",
                style = textStyle,
            ) {
                if (sublist.isComplete) {
                    Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                } else {
//                    NumberOfItemsChip(
//                        displayNumber = parentListWithSubLists.subLists.filter { !it.isComplete }.size.toString(),
//                        color = ItemColor,
//                        textColor = onItemColor
//                    )
                }
            }
        }
        AddEditListDialog(
            showEditListDialog,
            label = "Edit List",
            currentName = sublist.name,
            currentColor = sublist.color
        ) { newListName, newListColor, newTextColor ->
            val editedList =
                sublist.copy(name = newListName, color = newListColor)
            newTextColor?.let {
                editedList.textColor = it
            }

            viewModel.updateSubList(editedList)
            showEditListDialog.value = false
        }
    }
}