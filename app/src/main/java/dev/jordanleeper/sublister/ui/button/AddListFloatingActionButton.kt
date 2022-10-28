package dev.jordanleeper.sublister.ui.button


import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import dev.jordanleeper.sublister.R

@Composable
fun AddListFloatingActionButton(
    showAddListDialog: MutableState<Boolean>,
    color: Color = MaterialTheme.colorScheme.primary,
    iconTextColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val haptic = LocalHapticFeedback.current
    FloatingActionButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            showAddListDialog.value = true
        },
        containerColor = color
    ) {
        Icon(
            painter = painterResource(id = R.drawable.format_list_bulleted_add_24),
            "Add list",
            tint = iconTextColor
        )
    }
}