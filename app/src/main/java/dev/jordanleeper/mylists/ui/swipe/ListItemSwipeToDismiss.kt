package dev.jordanleeper.mylists.ui.swipe

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.data.ParentListWithSubLists
import dev.jordanleeper.mylists.ui.theme.Delete
import dev.jordanleeper.mylists.ui.theme.MarkCompleted

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemSwipeToDismiss(
    viewModel: ListViewModel,
    item: ParentListWithSubLists,
    content: @Composable() () -> Unit
) {
    val dismissState = rememberDismissState(confirmStateChange = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            viewModel.deleteList(item.parentList)
        }
        if (it == DismissValue.DismissedToEnd) {
            item.parentList.isComplete = !item.parentList.isComplete
            viewModel.updateParentList(item.parentList)
            shouldDismiss = false
        }
        shouldDismiss
    })

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.25f) },
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.Default -> MaterialTheme.colorScheme.surfaceVariant
                    DismissValue.DismissedToStart -> Delete
                    DismissValue.DismissedToEnd -> MarkCompleted
                }
            )

            val icon = when (direction) {
                DismissDirection.StartToEnd -> Icons.Default.Done
                DismissDirection.EndToStart -> Icons.Default.Delete
            }

            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(10.dp),
                contentAlignment = alignment
            ) {
                Icon(icon, contentDescription = "Delete", tint = Color.White)
            }
        },
        dismissContent = { content() }
    )
}