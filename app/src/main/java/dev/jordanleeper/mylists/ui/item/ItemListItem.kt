package dev.jordanleeper.mylists.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.ItemColor
import dev.jordanleeper.mylists.ui.theme.onItemColor

@Composable
fun ItemListItem(item: Item, viewModel: ParentListActivityViewModel) {
    val haptic = LocalHapticFeedback.current

    val textColor = when (item.isComplete) {
        true -> MaterialTheme.colorScheme.onSurfaceVariant
        false -> onItemColor
    }

    val boxBackground = when (item.isComplete) {
        true -> MaterialTheme.colorScheme.surfaceVariant
        false -> ItemColor
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
                .background(boxBackground)
                .padding(20.dp)
        ) {
            Text(item.name ?: "", color = textColor)
        }
    }

}