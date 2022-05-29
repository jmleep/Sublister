package dev.jordanleeper.mylists.ui.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissValue
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.MarkCompleted
import dev.jordanleeper.mylists.ui.theme.getColor

@Composable
fun ParentListItem(viewModel: ParentListActivityViewModel, subList: SubList) {
    ListItemSwipeToDismiss(onSwipe = {
        var shouldDismiss = true
        if (it == DismissValue.DismissedToStart) {
            viewModel.deleteSubList(subList)
        }
        if (it == DismissValue.DismissedToEnd) {
            subList.isComplete = !subList.isComplete
            viewModel.updateSubList(subList)
            shouldDismiss = false
        }
        shouldDismiss
    }) {
        val textColor = when (subList.isComplete) {
            true -> MaterialTheme.colorScheme.onSurfaceVariant
            false -> subList.textColor.getColor()

        }

        val boxBackground = when (subList.isComplete) {
            true -> MaterialTheme.colorScheme.surfaceVariant
            false -> subList.color.getColor()
        }

        Box(
            modifier = Modifier
                .background(boxBackground)
                .fillMaxWidth()
                .clickable {
                    println("clicked ${subList.name}")
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    subList.name ?: "List",
                    color = textColor,
                    fontSize = 20.sp,
                )

                if (subList.isComplete) {
                    Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                }
            }
        }
    }
}