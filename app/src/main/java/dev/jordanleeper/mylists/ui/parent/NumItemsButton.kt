package dev.jordanleeper.mylists.ui.parent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentList
import dev.jordanleeper.mylists.ui.theme.getColor

@Composable
fun NumItemsButton(
    isExpanded: MutableState<Boolean>,
    parentList: ParentList,
    items: List<Item>
) {
    OutlinedButton(
        onClick = { isExpanded.value = !isExpanded.value },
        shape = CircleShape,
        border = BorderStroke(1.dp, parentList.color.getColor()),
        colors = ButtonDefaults.buttonColors(backgroundColor = parentList.color.getColor())
    ) {
        Text(
            "${items.size}",
            color = parentList.textColor.getColor(),
        )
        if (isExpanded.value) {
            Icon(
                imageVector = Icons.Default.ExpandLess,
                "expand more",
                tint = parentList.textColor.getColor()
            )
        } else {
            Icon(
                imageVector = Icons.Default.ExpandMore,
                "collapse",
                tint = parentList.textColor.getColor()
            )
        }
    }
}