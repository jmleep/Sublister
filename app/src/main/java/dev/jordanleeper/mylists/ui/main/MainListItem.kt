package dev.jordanleeper.mylists.ui.main

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jordanleeper.mylists.data.MainActivityViewModel
import dev.jordanleeper.mylists.data.ParentListWithSubLists
import dev.jordanleeper.mylists.ui.parent.ParentListActivity
import dev.jordanleeper.mylists.ui.swipe.ListItemSwipeToDismiss
import dev.jordanleeper.mylists.ui.theme.MarkCompleted
import dev.jordanleeper.mylists.ui.theme.getColor

@Composable
fun MainListItem(item: ParentListWithSubLists, viewModel: MainActivityViewModel) {
    val context = LocalContext.current

    ListItemSwipeToDismiss(viewModel = viewModel, item = item) {
        val textColor = when (item.parentList.isComplete) {
            true -> MaterialTheme.colorScheme.onSurfaceVariant
            false -> item.parentList.textColor.getColor()//MaterialTheme.colorScheme.onSurface
        }

        val boxBackground = when (item.parentList.isComplete) {
            true -> MaterialTheme.colorScheme.surfaceVariant
            false -> item.parentList.color.getColor()
        }

        val fontWeight = if (item.subLists.isNotEmpty()) FontWeight.Bold else FontWeight.Normal

        Box(
            modifier = Modifier
                .background(boxBackground)
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(
                        context,
                        ParentListActivity::class.java
                    )
                    intent.putExtra("parentListId", item.parentList.id);
                    context.startActivity(intent)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    item.parentList.name ?: "List",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = fontWeight
                )

                if (item.parentList.isComplete) {
                    Icon(Icons.Default.Done, "Done", tint = MarkCompleted)
                }
            }
        }
    }
}