package dev.jordanleeper.mylists.ui.button


import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AddListFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick, containerColor = MaterialTheme.colorScheme.primary) {
        Icon(
            Icons.Filled.Add,
            "Add list",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}