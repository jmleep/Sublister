package dev.jordanleeper.mylists.ui.listitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    color: Color,
    label: String,
    style: TextStyle = TextStyle.Default,
    chipContent: @Composable() () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(5.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
        )

        Box(modifier = Modifier.padding(start = 15.dp)) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurface,//parentListWithSubLists.parentList.textColor.getColor(),
                style = style
            )
        }
        Spacer(modifier = Modifier.weight(1F))

        chipContent()
    }
}