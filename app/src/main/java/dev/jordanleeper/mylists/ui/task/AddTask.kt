package dev.jordanleeper.mylists.ui.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import dev.jordanleeper.mylists.data.Item
import dev.jordanleeper.mylists.data.ParentListActivityViewModel
import dev.jordanleeper.mylists.data.SubList
import dev.jordanleeper.mylists.ui.theme.getColor
import java.util.*

@Composable
fun AddTask(
    text: MutableState<String>,
    viewModel: ParentListActivityViewModel,
    subList: SubList,
    itemsList: List<Item>
) {
    val haptic = LocalHapticFeedback.current

    Row(
        modifier = Modifier
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                top = 5.dp,
                bottom = 5.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = subList.color.getColor(),
                unfocusedIndicatorColor = subList.color.getColor()
            ),
            modifier = Modifier
                .weight(1F)
                .padding(end = 15.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = subList.color.getColor()),
            border = BorderStroke(width = 1.dp, color = subList.color.getColor()),
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.addItem(
                    Item(
                        name = text.value,
                        subListId = subList.id,
                        dateCreated = Date().time,
                        position = itemsList.size + 1
                    )
                )
                text.value = ""
            }
        ) {
            Text("Add", color = subList.textColor.getColor())
        }
    }
}