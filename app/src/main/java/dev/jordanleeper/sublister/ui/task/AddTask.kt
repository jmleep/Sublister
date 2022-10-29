package dev.jordanleeper.sublister.ui.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jordanleeper.sublister.data.Item
import dev.jordanleeper.sublister.data.ParentListActivityViewModel
import dev.jordanleeper.sublister.data.Sublist
import dev.jordanleeper.sublister.ui.theme.getColor
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    text: MutableState<String>,
    viewModel: ParentListActivityViewModel,
    subList: Sublist,
    itemsList: List<Item>
) {
    val haptic = LocalHapticFeedback.current
    val isError = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1F)) {
                TextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                        if (it !== "") {
                            isError.value = false
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = subList.color.getColor(),
                        unfocusedIndicatorColor = subList.color.getColor()
                    ),
                    isError = isError.value
                )
            }

            Button(
                modifier = Modifier.padding(start = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = subList.color.getColor()),
                border = BorderStroke(width = 1.dp, color = subList.color.getColor()),
                onClick = {
                    if (text.value !== "") {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.addItem(
                            Item(
                                name = text.value,
                                sublistId = subList.id,
                                dateCreated = Date().time,
                                position = itemsList.size + 1
                            )
                        )
                        text.value = ""
                    } else {
                        isError.value = true
                    }
                }
            ) {
                Text("Add", color = subList.textColor.getColor())
            }
        }
        if (isError.value) {
            Row(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    "Please enter a value",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 10.sp,
                )
            }
        }
    }
}