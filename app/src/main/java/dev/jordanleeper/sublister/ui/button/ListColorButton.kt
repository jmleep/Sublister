package dev.jordanleeper.sublister.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.jordanleeper.sublister.ui.theme.getColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListColorButton(color: String, isChecked: Boolean, onChange: (Boolean) -> Unit) {
    FilledIconToggleButton(
        checked = isChecked,
        onCheckedChange = onChange
    ) {
        Box(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .background(color.getColor())
        )
    }
}