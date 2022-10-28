package dev.jordanleeper.sublister.ui.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun AddTaskFloatingActionButton(
    showAddListDialog: MutableState<Boolean>,
    color: Color?,
    textColor: Color?
) {
    val haptic = LocalHapticFeedback.current
    FloatingActionButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            showAddListDialog.value = true
        },
        containerColor = color ?: MaterialTheme.colorScheme.primary
    ) {
        Icon(
            Icons.Filled.AddBox,
            "Add task",
            tint = textColor ?: MaterialTheme.colorScheme.onPrimary,
        )
    }
}