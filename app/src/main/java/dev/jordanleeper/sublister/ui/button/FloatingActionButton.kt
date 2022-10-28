package dev.jordanleeper.sublister.ui.button


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun AddListFloatingActionButton(showAddListDialog: MutableState<Boolean>) {
    val haptic = LocalHapticFeedback.current
    FloatingActionButton(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            showAddListDialog.value = true
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            Icons.Filled.Add,
            "Add list",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}