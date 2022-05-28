package dev.jordanleeper.mylists.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jordanleeper.mylists.data.ListViewModel
import dev.jordanleeper.mylists.data.ParentListWithSubLists
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.MarkCompleted

@Composable
fun ParentListItem(item: ParentListWithSubLists, viewModel: ListViewModel) {
    ListItemSwipeToDismiss(viewModel = viewModel, item = item) {
        val textColor = when (item.parentList.isComplete) {
            true -> MaterialTheme.colorScheme.onSurfaceVariant
            false -> MaterialTheme.colorScheme.onSurface
        }

        val boxBackground = when (item.parentList.isComplete) {
            true -> MaterialTheme.colorScheme.surfaceVariant
            false -> MaterialTheme.colorScheme.surface
        }

        Box(
            modifier = Modifier
                .background(boxBackground)
                .padding(25.dp)
                .fillMaxWidth(),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    item.parentList.name ?: "List",
                    color = textColor
                )

                if (item.parentList.isComplete) {
                    Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                }
            }
        }
    }
}