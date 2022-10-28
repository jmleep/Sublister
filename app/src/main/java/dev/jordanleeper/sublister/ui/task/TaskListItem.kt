package dev.jordanleeper.sublister.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.jordanleeper.sublister.data.Item
import dev.jordanleeper.sublister.data.ParentListActivityViewModel
import dev.jordanleeper.sublister.ui.swipe.ListItemSwipeToDismiss

@Composable
fun TaskListItem(item: Item, viewModel: ParentListActivityViewModel) {
    val haptic = LocalHapticFeedback.current

    val textStyle = when (item.isComplete) {
        true -> TextStyle(textDecoration = TextDecoration.LineThrough)
        false -> TextStyle(textDecoration = TextDecoration.None)
    }

    ListItemSwipeToDismiss(onSwipe = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            viewModel.deleteItem(item)
        }
        if (it == DismissValue.DismissedToEnd) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            val updatedItem = item.copy()
            updatedItem.isComplete = !updatedItem.isComplete

            viewModel.updateItem(updatedItem)
            shouldDismiss = false
        }
        shouldDismiss
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
                .height(20.dp)
        ) {
            Text(
                "${item.name} ${item.position}",
                color = MaterialTheme.colorScheme.onSurface,
                style = textStyle
            )
        }
    }
}