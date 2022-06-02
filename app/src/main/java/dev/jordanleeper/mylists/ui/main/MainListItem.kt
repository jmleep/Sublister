package dev.jordanleeper.mylists.ui.main

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.MainActivityViewModel
import dev.jordanleeper.mylists.data.ParentListWithSubLists
import dev.jordanleeper.mylists.ui.dialog.AddEditListDialog
import dev.jordanleeper.mylists.ui.parent.NumberOfItemsChip
import dev.jordanleeper.mylists.ui.parent.ParentListActivity
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainListItem(
    parentListWithSubLists: ParentListWithSubLists,
    viewModel: MainActivityViewModel
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val showEditListDialog = remember { mutableStateOf(false) }

    ListItemSwipeToDismiss(onSwipe = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            viewModel.deleteList(parentListWithSubLists.parentList)
        }
        if (it == DismissValue.DismissedToEnd) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            val newList = parentListWithSubLists.parentList.copy()
            newList.isComplete = !newList.isComplete

            viewModel.updateParentList(newList)
            shouldDismiss = false
        }
        shouldDismiss
    }) {
        val textStyle = when (parentListWithSubLists.parentList.isComplete) {
            true -> TextStyle(textDecoration = TextDecoration.LineThrough)
            false -> TextStyle(textDecoration = TextDecoration.None)
        }

        val fontWeight =
            if (parentListWithSubLists.subLists.isNotEmpty()) FontWeight.Bold else FontWeight.Normal

        Box(
            modifier = Modifier
                .background(parentListWithSubLists.parentList.color.getColor())
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        val intent = Intent(
                            context,
                            ParentListActivity::class.java
                        )
                        intent.putExtra("parentListId", parentListWithSubLists.parentList.id);
                        context.startActivity(intent)
                    },
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showEditListDialog.value = true
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    parentListWithSubLists.parentList.name ?: "List",
                    color = parentListWithSubLists.parentList.textColor.getColor(),
                    fontSize = 20.sp,
                    fontWeight = fontWeight,
                    style = textStyle
                )

                if (parentListWithSubLists.parentList.isComplete) {
                    Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                } else {
                    NumberOfItemsChip(
                        displayNumber = parentListWithSubLists.subLists.filter { !it.isComplete }.size.toString(),
                        color = ItemColor,
                        textColor = onItemColor
                    )
                }
            }
        }
        AddEditListDialog(
            showEditListDialog,
            label = "Edit List",
            colors = parentListPalette.colors,
            textColors = parentListPalette.textColors
        ) { newListName, newListColor, myTextColor ->
            val editedList = parentListWithSubLists.parentList.copy()
            editedList.name = newListName
            editedList.color = newListColor
            editedList.textColor = myTextColor

            viewModel.updateParentList(editedList)
            showEditListDialog.value = false
        }
    }
}