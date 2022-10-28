package dev.jordanleeper.sublister.ui.listitem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberOfItemsChip(
    isExpanded: MutableState<Boolean>? = null,
    displayNumber: String,
    color: Color,
    textColor: Color
) {
    Box(modifier = Modifier.padding(end = 15.dp)) {
        OutlinedButton(
            onClick = {
                isExpanded?.let { isExpanded.value = !isExpanded.value }
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
        ) {
            Text(
                displayNumber,
                color = textColor,
                style = MaterialTheme.typography.labelLarge
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
}