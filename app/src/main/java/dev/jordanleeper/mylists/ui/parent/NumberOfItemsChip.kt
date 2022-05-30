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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberOfItemsChip(
    isExpanded: MutableState<Boolean>? = null,
    displayNumber: String,
    color: Color,
    textColor: Color
) {
    OutlinedButton(
        onClick = {
            isExpanded?.let { isExpanded.value = !isExpanded.value }
        },
        shape = CircleShape,
        border = BorderStroke(2.dp, color),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            displayNumber,
            color = textColor,
        )
        isExpanded?.let {
            if (isExpanded.value) {
                Icon(
                    imageVector = Icons.Default.ExpandLess,
                    "expand more",
                    tint = textColor
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    "collapse",
                    tint = textColor
                )
            }
        }
    }
}