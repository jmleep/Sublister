package dev.jordanleeper.sublister.ui.listdetail

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import dev.jordanleeper.sublister.data.ParentList
import dev.jordanleeper.sublister.ui.theme.getColor

@Composable
fun ListDetailAppBar(
    showAddListDialog: MutableState<Boolean>,
    textColor: Color,
    list: ParentList?
) {
    TopAppBar(
        actions = {
            IconButton(onClick = { showAddListDialog.value = true }) {
                Icon(Icons.Default.Add, "Add Sublist", tint = textColor)
            }
        },
        title = {
            Text(
                list?.name ?: "",
                fontSize = 20.sp,
                color = textColor
            )
        },
        backgroundColor = list?.color?.getColor() ?: Color.Black
    )
}